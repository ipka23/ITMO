package servlets;

import com.google.gson.Gson;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "ControllerServlet", value = "/controller")
public class ControllerServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("x") != null && request.getParameter("y") != null && request.getParameter("r") != null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/checkArea");
            dispatcher.forward(request, response);
        } else if (request.getParameter("x") == null || request.getParameter("y") == null || request.getParameter("r") == null){
            jsonResponse("error", "Неверное тело запроса! Введите по шаблону: \"?x=value1&y=value2&r=value3\"", response);
        } else {
            jsonResponse("error", "Ошибка выполнения", response);
        }
    }

    private static void jsonResponse(String status, String message, HttpServletResponse response) throws IOException {
//        Gson gson = new Gson();
//        Map<String, String> json = new HashMap<>();
        String s = "{\"status\":\"" + status + "\", " +
                "\"result\":" + message + "}";
//        json.put("status", status);
//        json.put("message", message);
        response.addHeader("Content-Type", "application/json");
        response.getWriter().write(s);
    }
}
