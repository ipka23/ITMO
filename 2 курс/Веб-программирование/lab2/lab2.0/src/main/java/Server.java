import com.fastcgi.FCGIInterface;
import utility.CoordinatesValidator;
import utility.HitChecker;
import utility.Point;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Server {
    static HashMap<String, Point> storageItems = new HashMap<>();
    static String storageLength;

    public static void main(String[] args) {
        var fcgiInterface = new FCGIInterface();
        while (fcgiInterface.FCGIaccept() >= 0) {
            long startTime = System.nanoTime();
            boolean get = true;
            var method = FCGIInterface.request.params.getProperty("REQUEST_METHOD");
            var contentType = FCGIInterface.request.params.getProperty("CONTENT_TYPE");
            Map<String, String> coords;
            if (method.equals("POST")) {
                addToLog("method: POST");
                try {
                    String requestBody;
                    if (contentType.equals("application/json")) {
                        addToLog("contentType: application/json");
                        requestBody = getRequestBody();
                        addToLog("jsonRequestBody: " + requestBody);
                        System.out.println(jsonResponse("success", "localStorage item получен успешно!", false));
                        continue;
                    } else {
                        get = false;

                        requestBody = getRequestBody();
                        if (requestBody.isEmpty()) {
                            System.out.println(jsonResponse("success", "Введите не пустое тело запроса!", false));
                            continue;
                        }
                        coords = parseCoords(requestBody);
                        if (!coords.containsKey("x") || !coords.containsKey("y") || !coords.containsKey("r")) {
                            System.out.println(jsonResponse("success", "Неверное тело запроса! Введите по шаблону: \"?x=value1&y=value2&r=value3\"", false));
                            continue;
                        }
                    }
                } catch (IOException e) {
                    System.out.println(jsonResponse("error", "Ошибка ввода-вывода!", false));
                    continue;
                }

            } else if (method.equals("GET")) {
                var queryString = FCGIInterface.request.params.getProperty("QUERY_STRING");
                if (queryString != null && !queryString.isEmpty()) {
                    String[] qs = queryString.split("=", 2);
                    addToLog("qs: " + queryString + "\nqs [] len: " + qs.length);
                    if (qs.length == 2) {
                        if (qs[0].equals("storageLength")) Server.storageLength = qs[1];
//                        if (qs[0].equals("itemIndex"))TODO itemIndex
                        System.out.println(jsonResponse("success", "localStorage.length получена успешно!", false));
                        continue;
                    } else {
                        coords = parseCoords(queryString);
                        if (!coords.containsKey("x") || !coords.containsKey("y") || !coords.containsKey("r")) {
                            System.out.println(htmlErrorResponse("Неверная строка запроса! Введите по шаблону: \"?x=value1&y=value2&r=value3\""));
                            continue;
                        }
                    }

                } else {
                    System.out.println(htmlErrorResponse("Введите не пустой запрос!"));
                    continue;
                }

            } else {
                System.out.println(htmlErrorResponse("отправьте POST или GET запрос вместо " + method + " запроса!"));
                continue;
            }
            ValidateResponse validateCoords = CoordinatesValidator.validate(coords);
            if (validateCoords.getStatus()) {
                String hit;
                String x = coords.get("x");
                String y = coords.get("y");
                String r = coords.get("r");
                if (HitChecker.check(coords)) {
                    hit = "Попадание";
                } else hit = "Промах";


                long endTime = System.nanoTime();
                String currentTime = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(Calendar.getInstance().getTime());
                double t = (endTime - startTime) / 1_000_000d;
                String executionTime = String.format("%.2fms", t);
                String s;
                Point point = new Point(x, y, r, hit, currentTime, executionTime);
                Server.storageItems.put(Server.storageLength, point);
//
                addToLog("\nindex: " + Server.storageLength + "\npoint: " + point);

                if (!get) {
                    s = "{\"x\":\"" + x +
                            "\",\"y\":\"" + y +
                            "\",\"r\":\"" + r +
                            "\",\"status\":\"" + hit +
                            "\",\"currentTime\":\"" + currentTime +
                            "\",\"executionTime\":\"" + executionTime + "\"}";
                    System.out.println(jsonResponse("success", s, true));
                } else {
                    s = "X: " + x +
                            "\nY: " + y +
                            "\nR: " + r +
                            "\nСтатус: " + hit +
                            "\nТекущее время: " + currentTime +
                            "\nВремя выполнения: " + executionTime;
                    System.out.println(htmlSuccessResponse(s));
                }
            } else {
                if (!get) {
                    System.out.println(jsonResponse("error", validateCoords.getMessage(), false));
                } else {
                    System.out.println(htmlErrorResponse(validateCoords.getMessage()));
                }
            }
        }
    }


    private static String getRequestBody() throws IOException {
        String contentLengthStr = FCGIInterface.request.params.getProperty("CONTENT_LENGTH");
        if (contentLengthStr == null || contentLengthStr.isEmpty()) {
            return "";
        }
        int contentLength;
        try {
            contentLength = Integer.parseInt(contentLengthStr);
        } catch (NumberFormatException e) {
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
        String s = new String(buffer, 0, totalRead);
        addToLog("str: " + s);
        return s;
    }

    private static String jsonResponse(String status, String message, boolean json) {
        String s;
        if (json) {
            s = "Content-Type: application/json\n" +
                    "\r\n\r\n" + "{\"status\":\"" + status + "\", " +
                    "\"result\":" + message + "}";
        } else {
            s = "Content-Type: application/json\n" +
                    "\r\n\r\n" + "{\"status\":\"" + status + "\", " +
                    "\"result\":\"" + message + "\"}";
        }
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

    public static void addToLog(String message) {
        try (PrintWriter logger = new PrintWriter(new FileWriter("/home/studs/s467204/httpd-root/fcgi-bin/log.txt", StandardCharsets.UTF_8, true))) {
            String time = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(Calendar.getInstance().getTime());
            logger.println("[ " + time + " ]" + message);
        } catch (IOException e) {
            System.out.println(jsonResponse("error", "Ошибка ввода-вывода!", false));
        }
    }
}