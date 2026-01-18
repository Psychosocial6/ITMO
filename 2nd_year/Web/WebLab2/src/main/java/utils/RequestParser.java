package utils;

import jakarta.servlet.http.HttpServletRequest;
import objects.RequestBody;

import java.math.BigDecimal;

public class RequestParser {

    public static RequestBody parseRequest(HttpServletRequest request) {
        try {
            BigDecimal x = new BigDecimal(request.getParameter("x"));
            BigDecimal y = new BigDecimal(request.getParameter("y"));
            BigDecimal r = new BigDecimal(request.getParameter("r"));
            String type = request.getParameter("type");
            return new RequestBody(x, y, r, type);
        }
        catch (Exception e) {
            return null;
        }
    }
}