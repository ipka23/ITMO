import com.fastcgi.FCGIInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Server {
    public static void main(String[] args) {
//        System.err.println("Java FCGI Server starting...");
//        System.err.println("Current dir: " + System.getProperty("user.dir"));
//
        var fcgiInterface = new FCGIInterface();
//
//        System.err.println("FCGI Interface created");
//
        while (fcgiInterface.FCGIaccept() >= 0) {
            boolean get = true;
//
//            System.err.println("Request accepted");
//
            var startTime = System.nanoTime();
            var method = FCGIInterface.request.params.getProperty("REQUEST_METHOD");
//
//            System.err.println("method: " + method);
//
            Map<String, String> coords;
            if (method.equals("POST")) {
                get = false;
                String requestBody;
                try {
                    requestBody = getRequestBody();
//
//                    System.err.println(requestBody);
//
                } catch (IOException e) {
                    System.out.println(jsonErrorResponse("Ошибка ввода-вывода!"));
                    continue;
                }
                coords = parseCoords(requestBody);
//
//                for (Map.Entry<String, String> entry : coords.entrySet()) {
//                    System.out.println(entry.getKey() + ": " + entry.getValue());
//                }
//
            } else if (method.equals("GET")) {
                var queryString = FCGIInterface.request.params.getProperty("QUERY_STRING");
                coords = parseCoords(queryString);
            } else {
                System.out.println(jsonErrorResponse("отправьте POST или GET запрос вместо " + method + " запроса!"));
                continue;
            }
            ValidateResponse validateCoords = CoordinatesValidator.validate(coords);
            if (validateCoords.getStatus()) {
                String hit;
                if (HitChecker.check(coords)) {
                    hit = "Попадание";
                } else hit = "Промах";

                var endTime = System.nanoTime();

                Date currentTime = Calendar.getInstance().getTime();
                String executionTime = (endTime - startTime) / 1_000_000 + "ms";
                String s;
                if (!get) {
                    s = "{\"x\":\"" + coords.get("x") +
                            "\",\"y\":\"" + coords.get("y") +
                            "\",\"r\":\"" + coords.get("r") +
                            "\",\"status\":\"" + hit +
                            "\",\"currentTime\":\"" + currentTime +
                            "\",\"executionTime\":\"" + executionTime + "\"}";
                    System.out.println(jsonSuccessResponse(s));
                }
                else {
                    s = "X: " + coords.get("x") +
                            "\nY: " + coords.get("y") +
                            "\nR: " + coords.get("r") +
                            "\nСтатус: " + hit +
                            "\nТекущее время: " + currentTime +
                            "\nВремя выполнения: " + executionTime;
                    System.out.println(htmlSuccessResponse(s));
                }
            } else {
                if (!get) {
                    System.out.println(jsonErrorResponse(validateCoords.getMessage()));
                }
                else {
                    System.out.println(htmlErrorResponse(validateCoords.getMessage()));
                }
            }
        }
    }

//    private static String getRequestBody() throws IOException {
//        var contentLength = FCGIInterface.request.params.getProperty("CONTENT_LENGTH");
//        try (InputStreamReader isr = new InputStreamReader(System.in, StandardCharsets.UTF_8);
//             BufferedReader br = new BufferedReader(isr)) {
//            StringBuilder buf = new StringBuilder(contentLength);
//            int b;
//            while ((b = br.read()) != -1) {
//                buf.append((char) b);
//            }
//            return buf.toString();
//        }
//
//    }

    private static String getRequestBody() throws IOException {
        var contentLengthStr = FCGIInterface.request.params.getProperty("CONTENT_LENGTH");
        if (contentLengthStr == null || contentLengthStr.isEmpty()) {
            return "";
        }

        int contentLength;
        try {
            contentLength = Integer.parseInt(contentLengthStr);
        } catch (NumberFormatException e) {
            System.err.println("Invalid Content-Length: " + contentLengthStr);
            return "";
        }

        if (contentLength <= 0) {
            return "";
        }

        char[] buffer = new char[contentLength];
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int totalRead = 0;
        while (totalRead < contentLength) {
            int read = reader.read(buffer, totalRead, contentLength - totalRead);
            if (read == -1) {
                break;
            }
            totalRead += read;
        }

        return new String(buffer, 0, totalRead);
    }

    private static String jsonSuccessResponse(String jsonStr) {
        String s = "Content-Type: application/json\n" +
                "\r\n\r\n" + jsonStr;
//
        System.err.println(s);
//
        return s;
    }

    private static String jsonErrorResponse(String message) {
        String s = "Content-Type: application/json\n" +
                "\r\n\r\n" + "{\"error\":\"" + message + "\"}";
//
        System.err.println(s);
//
        return s;
    }

    private static String htmlSuccessResponse(String message) {
        message = message.replace("\n", "<br>");
        String s = "Content-Type: text/html\n" +
                "\r\n\r\n" +
                """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>Веб лаба 1</title>
                </head>
                <body>
                    <h1 style="color: green">Результат выполнения</h1>
                    <p>""" + message + """
                </p>
                </body>
                </html>
                """;
        return s;
    }

    private static String htmlErrorResponse(String message) {
        String s = "Content-Type: text/html\n" +
                "\r\n\r\n" +
                """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>Веб лаба 1</title>
                </head>
                <body>
                    <h1 style="color: red">Ошибка выполнения</h1>
                    <p>Описание:""" + message + """
                </p>
                </body>
                </html>
                """;
        return s;
    }

    private static Map<String, String> parseCoords(String s) {
        Map<String, String> params = new HashMap<>();
        String[] pairs = s.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            params.put(keyValue[0], keyValue[1]);
        }
        return params;
    }
}