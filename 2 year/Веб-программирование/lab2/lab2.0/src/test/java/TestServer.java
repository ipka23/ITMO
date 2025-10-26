import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TestServer {
    public static void main(String[] args) {
//        Map<String, String> coords = new HashMap<>();
//        coords.put("x", "0");
//        coords.put("y", "2");
//        coords.put("r", "2");
////        System.out.println(utility.HitChecker.check(coords));
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
//        String time = simpleDateFormat.format(Calendar.getInstance().getTime());
//
//        System.out.println("[" + time + "]");
//        utility.Point p = new utility.Point("1", "1", "1", "1", "1", "1");
//        System.out.println(p);

        String s = "{\"x\":\"" + 1 +
                "\",\"y\":\"" + 1 +
                "\",\"r\":\"" + 1 +
                "\",\"status\":\"" + 1 +
                "\",\"currentTime\":\"" + 1 +
                "\",\"executionTime\":\"" + 1 + "\"}";
        String json = "{\"status\":\"" + "success" + "\", " +
                "\"result\":" + s + "}";
        System.out.println(json);
    }
}
