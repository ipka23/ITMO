package servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utility.HitChecker;
import utility.Point;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

@WebServlet(name = "AreaCheckServlet", value = "/checkArea")
public class AreaCheckServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long startTime = System.nanoTime();
        Point p = makePoint(request, startTime);
        updatePoints(request, p);
        String s = "{\"x\":\"" + p.getX() +
                "\",\"y\":\"" + p.getY() +
                "\",\"r\":\"" + p.getR() +
                "\",\"status\":\"" + p.getHit() +
                "\",\"currentTime\":\"" + p.getCurrentTime() +
                "\",\"executionTime\":\"" + p.getExecutionTime() + "\"}";
        response.getWriter().write(s);
    }

    public Point makePoint(HttpServletRequest request, long startTime) {
        String hit;
        String x = request.getParameter("x");
        String y = request.getParameter("y");
        String r = request.getParameter("r");
        if (HitChecker.check(x, y, r)) {
            hit = "Попадание";
        } else hit = "Промах";
        String currentTime = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(Calendar.getInstance().getTime());
        long endTime = System.nanoTime();
        double t = (endTime - startTime) / 1_000_000d;
        String executionTime = String.format("%.2fms", t);
        Point p = new Point(x, y, r, currentTime, hit, executionTime);
        return p;
    }

    public void updatePoints(HttpServletRequest request, Point p) {
        HttpSession httpSession = request.getSession();
        ArrayList<Point> points;
        points = (ArrayList<Point>) request.getAttribute("points");
        if (httpSession.getAttribute("points") == null) {
            points = new ArrayList<>();
        }
        points.add(p);
        httpSession.setAttribute("points", points);
    }
}

