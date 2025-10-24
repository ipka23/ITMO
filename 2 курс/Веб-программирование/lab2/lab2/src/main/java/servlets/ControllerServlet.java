package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;
import utility.ResponseStatus;

import java.io.IOException;
@Log
public class ControllerServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            if (request.getParameter("x") != null && request.getParameter("y") != null && request.getParameter("r") != null) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/checkArea");
                log.info("QS: " + request.getQueryString());
                dispatcher.forward(request, response);
            }
        } catch (ServletException e) {
            jsonResponse(ResponseStatus.ERROR, e.getMessage(), response);
        }
    }

    private static void jsonResponse(ResponseStatus status, String message, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        String s = "{\"status\":\"" + status + "\", " +
                "\"result\":\"" + message + "\"}";
        response.getWriter().write(s);
    }
}
