import com.fastcgi.FCGIInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Server {
    public static void main(String[] args) {
        var fcgiInterface = new FCGIInterface();
        while (fcgiInterface.FCGIaccept() >= 0) {
            var startTime = System.nanoTime();
            var method = FCGIInterface.request.params.getProperty("REQUEST_METHOD");
            if (method.equals("POST")) {
                String requestBody;
                try {
                    requestBody = getRequestBody();
                } catch (IOException e) {
                    System.out.println(htmlErrorResponse("Ошибка ввода-вывода!"));
                    continue;
                }

                Map<String, String> coords = parseCoords(requestBody);

                ValidateResponse validateCoords = CoordinatesValidator.validate(coords);
                if (validateCoords.getStatus()) {
                    String hit;
                    if (HitChecker.check(coords)) {
                        hit = "Попадание";
                    } else hit = "Промах";

                    var endTime = System.nanoTime();

                    Date currentDate = Calendar.getInstance().getTime();
                    String executionTime = (endTime - startTime) / 1_000_000 + "ms";
                    String s = "X: " + coords.get("x") +
                            "\nY: " + coords.get("y") +
                            "\nR: " + coords.get("r") +
                            "\nСтатус: " + hit +
                            "\nТекущее время: " + currentDate +
                            "\nВремя выполнения: " + executionTime;

                    System.out.println(htmlSuccessResponse(s));
                } else {
                    System.out.println(htmlErrorResponse(validateCoords.getMessage()));
                }
            } else {
                System.out.println(htmlErrorResponse("отправьте POST запрос вместо " + method + " запроса!"));
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

    private static String htmlSuccessResponse(String message) {
        return """
                Content-Type: text/html\r\n\r\n<!DOCTYPE html>
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
    }

    private static String htmlErrorResponse(String message) {
        return """
                Content-Type: text/html\r\n\r\n<!DOCTYPE html>
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