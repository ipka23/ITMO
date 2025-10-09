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

@WebServlet(name = "areaCheckServlet", value = "/areaCheckServlet")
public class AreaCheckServlet extends HttpServlet {
    private static ArrayList<Point> points = new ArrayList<>();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long startTime = System.nanoTime();
        Point p = makePoint(request, startTime);
        HttpSession httpSession = request.getSession();

        points = (ArrayList<Point>) request.getAttribute("points");
        if (httpSession.getAttribute("points") == null) {
            httpSession.setAttribute("points", points);
        }
        points.add(p);
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
}

