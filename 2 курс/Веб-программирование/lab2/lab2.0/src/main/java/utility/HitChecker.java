package utility;

import java.math.BigDecimal;
import java.util.Map;
public class HitChecker {
    public static boolean check(Map<String, String> coords) {
        BigDecimal BDx = new BigDecimal(coords.get("x"));
        BigDecimal BDy =  new BigDecimal(coords.get("y"));
        BigDecimal BDr =  new BigDecimal(coords.get("r"));
        double x = BDx.doubleValue();
        double y = BDy.doubleValue();
        double r = BDr.doubleValue();
        if (x > 0 && y > 0) return false;
        if (x == 0 && y == 0) return true;
        return checkTriangle(x, y, r) || checkRectangle(x, y, r) || checkCircle(x, y, r);
    }

    private static boolean checkCircle(double x, double y, double r) {
        if (x >= 0 && y <= 0) {
            return Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(r, 2);
        }
        return false;
    }

    private static boolean checkRectangle(double x, double y, double r) {
        if (x <= 0 && y <= 0) {
            return x > -r / 2 && y > -r;
        }
        return false;
    }

    private static boolean checkTriangle(double x, double y, double r) {
        if (x <= 0 && y >= 0) {
            return x > - r / 2 && y > r / 2;
        }
        return false;
    }
}
