package utility;

public class CoordinatesValidator {

    public static ValidateResponse validate(String x, String y, String r) {
        ValidateResponse validatedX = validateX(x);
        ValidateResponse validatedY = validateY(y);
        ValidateResponse validatedR = validateR(r);

        if (validatedX.isValid()) {

            if (validatedY.isValid()) {

                if (validatedR.isValid()) {
                    return new ValidateResponse(true);

                } else return validatedR;

            } else return validatedY;

        } else return validatedX;
    }

    private static ValidateResponse validateX(String val) throws NumberFormatException {
        double x;
        double[] xValues = {-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2};
        boolean flag = false;
        try {
            x = Double.parseDouble(val);
            for (double value : xValues) {
                if (value == x) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                return new ValidateResponse(true);
            } else {
                return new ValidateResponse(false, "X должен принадлежать множеству: {-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2}");
            }

        } catch (NumberFormatException e) {
            return new ValidateResponse(false, "X должен являться числом!");
        }
    }

    private static ValidateResponse validateY(String val) {
        double y;
        try {
            y = Double.parseDouble(val);
            if (-3 <= y && y <= 3) {
                return new ValidateResponse(true);
            } else {
                return new ValidateResponse(false, "Y должен принадлежать отрезку: [-3, 3]!");
            }
        } catch (NumberFormatException e) {
            return new ValidateResponse(false, "Y должен являться числом!");
        }
    }

    private static ValidateResponse validateR(String val) {
        double r;
        try {
            r = Double.parseDouble(val);
            if (2 <= r && r <= 5) {
                return new ValidateResponse(true);
            } else {
                return new ValidateResponse(false, "R должен принадлежать отрезку: [2, 5]!");
            }
        } catch (NumberFormatException e) {
            return new ValidateResponse(false, "R должен являться числом!");
        }
    }

}
