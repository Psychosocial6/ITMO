package controllers;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import services.UserService;
import utils.JWTUtil;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    @EJB
    private UserService userService;

    @EJB
    private JWTUtil jwtUtil;

    @POST
    @Path("/login")
    public Response login(LoginRequest request) {
        boolean authenticated = userService.checkUser(request.getLogin(), request.getPassword());

        if (!authenticated) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new ErrorResponse("Неверные учетные данные"))
                    .build();
        }

        String token = jwtUtil.generateToken(request.getLogin());
        return Response.ok(new AuthResponse(token, request.getLogin())).build();
    }

    @POST
    @Path("/register")
    public Response register(LoginRequest request) {
        boolean registered = userService.registerUser(request.getLogin(), request.getPassword());

        if (!registered) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("Пользователь уже существует"))
                    .build();
        }

        return Response.ok(new MessageResponse("Пользователь успешно зарегистрирован")).build();
    }

    @POST
    @Path("/validate")
    public Response validateToken(@HeaderParam("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        try {
            String token = authHeader.substring(7);
            String username = jwtUtil.validateToken(token);
            return Response.ok(new AuthResponse(token, username)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new ErrorResponse("Невалидный токен"))
                    .build();
        }
    }

    @POST
    @Path("/logout")
    public Response logout(@HeaderParam("Authorization") String authHeader) {
        return Response.ok(new MessageResponse("Выход выполнен")).build();
    }

    public static class LoginRequest {
        private String login;
        private String password;

        public String getLogin() { return login; }
        public void setLogin(String login) { this.login = login; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class AuthResponse {
        private String token;
        private String username;
        private boolean success = true;

        public AuthResponse(String token, String username) {
            this.token = token;
            this.username = username;
        }

        public String getToken() { return token; }
        public String getUsername() { return username; }
        public boolean isSuccess() { return success; }
    }

    public static class MessageResponse {
        private String message;
        private boolean success = true;

        public MessageResponse(String message) {
            this.message = message;
        }

        public String getMessage() { return message; }
        public boolean isSuccess() { return success; }
    }

    public static class ErrorResponse {
        private String error;
        private boolean success = false;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() { return error; }
        public boolean isSuccess() { return success; }
    }
}