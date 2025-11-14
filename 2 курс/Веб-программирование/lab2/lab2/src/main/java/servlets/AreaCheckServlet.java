package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.java.Log;
import utility.*;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Log
@WebServlet(value = "/checkArea")
public class AreaCheckServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        long startTime = System.nanoTime();

        Point p = makePoint(request, startTime);

        if (p.isValid()) {
            updatePoints(request, p);
            if (Objects.equals(request.getParameter("drawByClick"), "false") || request.getParameter("drawByClick") == null) {

                RequestDispatcher dispatcher = request.getRequestDispatcher("/result");
                dispatcher.forward(request, response);

            } else {
                String s = "{\"x\":\"" + p.getX() +
                        "\",\"y\":\"" + p.getY() +
                        "\",\"r\":\"" + p.getR() +
                        "\",\"status\":\"" + p.getStatus() +
                        "\",\"currentTime\":\"" + p.getCurrentTime() +
                        "\",\"executionTime\":\"" + p.getExecutionTime() + "\"}";
                jsonResponse(response, ResponseStatus.SUCCESS, s, true);
            }

        } else {
            jsonResponse(response, ResponseStatus.ERROR, p.getValidationMessage(), false);
        }

    }

    private void jsonResponse(HttpServletResponse response, ResponseStatus status, String message, boolean json) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        String s;
        if (json) {
            s = "{\"status\":\"" + status.getStatus() + "\", " +
                    "\"result\":" + message + "}";
        } else {
            s = "{\"status\":\"" + status.getStatus() + "\", " +
                    "\"result\":\"" + message + "\"}";
        }
        response.getWriter().write(s);
    }

    private Point makePoint(HttpServletRequest request, long startTime) {
        String status;
        String x = request.getParameter("x");
        String y = request.getParameter("y");
        String r = request.getParameter("r");
        ValidateResponse validateCoords = CoordinatesValidator.validate(x, y, r);
        Point point = new Point();
        if (validateCoords.isValid()) {

            boolean hit = HitChecker.check(x, y, r);

            if (hit) {
                status = Localizer.getBundle().getString("hit");
            } else status = Localizer.getBundle().getString("miss");

            String currentTime = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(Calendar.getInstance().getTime());
            long endTime = System.nanoTime();
            double t = (endTime - startTime) / 1_000_000d;
            String executionTime = String.format("%.2fms", t);
            point = new Point(x, y, r, status, currentTime, executionTime);
            point.setValid(true);
        } else {
            point.setValid(false);
            point.setValidationMessage(validateCoords.getMessage());
        }
        return point;
    }

    private void updatePoints(HttpServletRequest request, Point p) {
        HttpSession httpSession = request.getSession();
        LinkedHashSet<Point> points;

        if (httpSession.getAttribute("points") == null) {
            points = new LinkedHashSet<>();
            httpSession.setAttribute("points", points);
        } else {
            points = (LinkedHashSet<Point>) httpSession.getAttribute("points");
        }
        if (!PointsStorage.exists(p)) PointsStorage.add(p);

        points.add(p);
        httpSession.setAttribute("points", points);
    }
}

