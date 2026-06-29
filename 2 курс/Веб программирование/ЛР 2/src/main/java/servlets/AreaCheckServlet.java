package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import objects.RequestBody;
import objects.Result;
import utils.AreaHitChecker;
import utils.RequestParser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@WebServlet("/areacheck")
public class AreaCheckServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestBody requestBody = RequestParser.parseRequest(req);

        boolean shotResult = AreaHitChecker.checkHit(requestBody);

        LocalDateTime localTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy");
        String stringTime = localTime.format(formatter);
        long endTime = System.nanoTime();
        long startTime = Long.parseLong(req.getAttribute("startTime").toString());
        double requestTime = Math.round(((double) (endTime - startTime) / 1e6) * 1e6) / 1e6;

        Result result = new Result(requestBody.getX(),
                requestBody.getY(),
                requestBody.getR(),
                shotResult,
                requestTime,
                stringTime);

        ServletContext context = getServletContext();
        saveResult(context, result);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
        dispatcher.forward(req, resp);
    }

    private void saveResult(ServletContext servletContext, Result result) {
        List<Result> results = (List<Result>) servletContext.getAttribute("results");

        if (results == null) {
            results = new CopyOnWriteArrayList<>();
        }

        results.add(result);
        servletContext.setAttribute("results", results);
    }
}
