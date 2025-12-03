package utility;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class HitChecker {

    public static boolean check(BigDecimal x, BigDecimal y, BigDecimal r) {
        if (x == null || y == null || r == null) {
            return false;
        }

        if (x.compareTo(BigDecimal.ZERO) > 0 && y.compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }

        if (x.compareTo(BigDecimal.ZERO) == 0 && y.compareTo(BigDecimal.ZERO) == 0) {
            return true;
        }

        return checkTriangle(x, y, r) || checkRectangle(x, y, r) || checkCircle(x, y, r);
    }

    private static boolean checkCircle(BigDecimal x, BigDecimal y, BigDecimal r) {

        if (x.compareTo(BigDecimal.ZERO) <= 0 && y.compareTo(BigDecimal.ZERO) <= 0) {
            BigDecimal xSquared = x.pow(2);
            BigDecimal ySquared = y.pow(2);
            BigDecimal sumOfSquares = xSquared.add(ySquared);

            BigDecimal halfR = r.divide(new BigDecimal("2"), 10, RoundingMode.HALF_UP);
            BigDecimal halfRSquared = halfR.pow(2);

            return sumOfSquares.compareTo(halfRSquared) <= 0;
        }
        return false;
    }

    private static boolean checkTriangle(BigDecimal x, BigDecimal y, BigDecimal r) {

        if (x.compareTo(BigDecimal.ZERO) >= 0 && y.compareTo(BigDecimal.ZERO) >= 0) {

            BigDecimal twoX = x.multiply(new BigDecimal("2"));
            BigDecimal negativeTwoX = twoX.negate();
            BigDecimal rightSide = negativeTwoX.add(r);

            return y.compareTo(rightSide) <= 0;
        }
        return false;
    }

    private static boolean checkRectangle(BigDecimal x, BigDecimal y, BigDecimal r) {

        if (x.compareTo(BigDecimal.ZERO) <= 0 && y.compareTo(BigDecimal.ZERO) >= 0) {
            BigDecimal halfRNegative = r.divide(new BigDecimal("2"), 10, RoundingMode.HALF_UP).negate();
            boolean xCondition = x.compareTo(halfRNegative) >= 0;

            boolean yCondition = y.compareTo(r) <= 0;

            return xCondition && yCondition;
        }
        return false;
    }

}