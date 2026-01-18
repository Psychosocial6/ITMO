package servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import objects.RequestBody;
import utils.RequestParser;
import utils.Validator;

import java.io.IOException;

@WebServlet("/api")
public class ControllerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestBody requestBody = RequestParser.parseRequest(req);

        if (requestBody == null || !Validator.validateData(requestBody)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid values");
            return;
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/areacheck");
        req.setAttribute("startTime", System.nanoTime());
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/delete");
        dispatcher.forward(req, resp);
    }
}
