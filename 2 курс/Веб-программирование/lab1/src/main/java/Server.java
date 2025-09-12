import com.fastcgi.FCGIInterface;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Server {
    public static void main(String[] args) throws IOException {
        var fcgiInterface = new FCGIInterface();
        while (fcgiInterface.FCGIaccept() >= 0) {
            var method = FCGIInterface.request.params.getProperty("REQUEST_METHOD");
            if (method.equals("POST")) {
                var queryString = FCGIInterface.request.params.getProperty("QUERY_STRING");
                Map<String, String> coords = parseCoords(queryString);
                var startTime = System.nanoTime();

                if (CoordinateValidator.validate(coords)){
                    String hit;
                    if (HitChecker.checkHit(coords)) {
                        hit = "Попадание";
                    } else hit = "Промах";

                    var endTime = System.nanoTime();
                    String executionTime = Long.toString(endTime - startTime);
                    Date currentDate = Calendar.getInstance().getTime();

                    String s = "X: " + coords.get("x") + "\nY: " + coords.get("y") + "\nСтатус: "
                            + hit + "\nВремя выполнения: " + executionTime + "\nТекущее время: " + currentDate;

                    System.out.println(htmlResponse(s));
                } else {
                    System.out.println(htmlResponse("Введены некорректные данные!"));
                }
            }

        }
    }

    private static String htmlResponse(String message){
        String response =
                """
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <title>Веб лаба 1</title>
        </head>
        <body>
            <h1>Результат выполнения</h1>
            <p>""" + message +
        """
        </p>
        </body>
        </html>
        """;

        return response;
    }
    private static Map<String, String> parseCoords(String s) {
        Map<String, String> params = new HashMap<>();
        String[] pairs = s.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            if (keyValue.length == 2) {
                params.put(keyValue[0], keyValue[1]);
            }
        }
        return params;
    }
}