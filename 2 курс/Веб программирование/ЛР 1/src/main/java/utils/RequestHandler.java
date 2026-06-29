package utils;

import com.fastcgi.FCGIRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class RequestHandler {
    private static final FilePrinter filePrinter = new FilePrinter(Path.of("logs/logs.txt"));
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static long startTime;
    private static long endTime;
    private static String captchaURL = "https://www.google.com/recaptcha/api/siteverify";

    private static final String RESPONSE_CREATED = """
                            Status: 201 Created
                            Content-Type: application/json
                            Content-Length: %d
                            
                            %s
                            
                            """;

    private static final String RESPONSE_BAD_REQUEST = """
                            Status: 400 Bad Request
                            Content-Type: application/json
                            Content-Length: %d
                            
                            %s
                            
                            """;

    private static final String RESPONSE_METHOD_NOT_ALLOWED = """
            Status: 405 Method Not Allowed
            Allow: POST, GET, DELETE
            """;

    private static final String RESPONSE_GET_OK = """
            Status: 200 OK
            Content-Type: application/json
            Content-Length: %d
            
            %s
            
            """;
    private static final String RESPONSE_DELETE_OK = """
            Status: 200 OK
            """;

    private static final String RESPONSE_CAPTCHA_RESULT = """
                            Status: 200 OK
                            Content-Type: application/json
                            Content-Length: %d
                            
                            %s
                            
                            """;

    static {
        objectMapper.registerModule(new JavaTimeModule());
    };

    public static void handleRequest(FCGIRequest request) throws IOException {
        String method = request.params.getProperty("REQUEST_METHOD");

        if ("POST".equals(method)) {
            startTime = System.nanoTime();
            filePrinter.getPrintWriter().println("processing POST request");
            String requestBody = getBody(request);

            if ("/api".equals(request.params.getProperty("REQUEST_URI"))) {
                RequestData requestData = objectMapper.readValue(requestBody, RequestData.class);
                float x = requestData.getX();
                BigDecimal y = requestData.getY();
                float r = requestData.getR();

                if (Validator.validateData(x, y, r)) {
                    filePrinter.getPrintWriter().println("request data validated successfully");
                    boolean result = AreaHitChecker.checkHit(x, y, r);
                    endTime = System.nanoTime();
                    filePrinter.getPrintWriter().println(endTime - startTime);
                    LocalDateTime time = LocalDateTime.now();
                    sendResponseCreated(new ResponseData(Math.round(((double) (endTime - startTime) / 1e6) * 1e6) / 1e6, time, result, "OK"));
                    TableItem tableItem = new TableItem(TableSave.getSize() + 1, x, y, r, result, Math.round(((double) (endTime - startTime) / 1e6) * 1e6) / 1e6, time);
                    TableSave.addItem(tableItem);
                } else {
                    filePrinter.getPrintWriter().println("invalid request data");
                    endTime = System.nanoTime();
                    sendResponseBadRequest(new ResponseData(Math.round(((double) (endTime - startTime) / 1e6) * 1e6) / 1e6, LocalDateTime.now(), false, "Invalid coordinates values"));
                }
            }

            else if ("/api/captcha".equals(request.params.getProperty("REQUEST_URI"))) {
                UserToken userToken = objectMapper.readValue(requestBody, UserToken.class);
                String token = userToken.getToken();
                filePrinter.getPrintWriter().println("token: " + token);

                URL url = new URL(captchaURL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                String params = "secret=" + System.getProperty("SECRET_KEY") + "&response=" + token;
                filePrinter.getPrintWriter().println("params: " + params);

                try (DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream())) {
                    wr.write(params.getBytes(StandardCharsets.UTF_8));
                    wr.flush();
                }

                StringBuilder googleResponseString = new StringBuilder();
                try (BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        googleResponseString.append(inputLine);
                    }
                }

                filePrinter.getPrintWriter().println("google response: " + googleResponseString.toString());
                GoogleCaptchaResponse googleCaptchaResponse = objectMapper.readValue(googleResponseString.toString(), GoogleCaptchaResponse.class);

                sendCaptchaResponse(googleCaptchaResponse);
            }
        }
        else if ("GET".equals(method)) {
            sendResponseGetOk();
        }
        else if ("DELETE".equals(method)) {
            TableSave.clear();
            sendResponseDeleteOk();
        }
        else {
            sendResponseMethodNotAllowed();
        }
    }

    private static String getBody(FCGIRequest request) throws IOException {
        int contentLength = Integer.parseInt(request.params.getProperty("CONTENT_LENGTH"));
        byte[] body = request.inStream.readNBytes(contentLength);
        return  new String(body, StandardCharsets.UTF_8);
    }

    private static void sendResponseCreated(ResponseData responseData) throws JsonProcessingException {
        String responseBody = objectMapper.writeValueAsString(responseData);
        System.out.println(String.format(RESPONSE_CREATED, responseBody.getBytes(StandardCharsets.UTF_8).length, responseBody));
    }

    private static void sendResponseBadRequest(ResponseData responseData) throws JsonProcessingException {
        String responseBody = objectMapper.writeValueAsString(responseData);
        System.out.println(String.format(RESPONSE_BAD_REQUEST, responseBody.getBytes(StandardCharsets.UTF_8).length, responseBody));
    }

    private static void sendResponseMethodNotAllowed() {
        System.out.println(RESPONSE_METHOD_NOT_ALLOWED);
    }

    private static void sendResponseGetOk() throws JsonProcessingException {
        String responseBody = TableSave.getTable();
        System.out.println(String.format(RESPONSE_GET_OK, responseBody.getBytes(StandardCharsets.UTF_8).length, responseBody));
    }

    private static void sendResponseDeleteOk() {
        System.out.println(RESPONSE_DELETE_OK);
    }

    private static void sendCaptchaResponse(GoogleCaptchaResponse googleCaptchaResponse) throws JsonProcessingException {
        String responseBody = objectMapper.writeValueAsString(googleCaptchaResponse);
        System.out.println(String.format(RESPONSE_CAPTCHA_RESULT, responseBody.getBytes(StandardCharsets.UTF_8).length, responseBody));
    }

}
