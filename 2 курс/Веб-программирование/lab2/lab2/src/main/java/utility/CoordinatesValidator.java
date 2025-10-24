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
        try {
            x = Double.parseDouble(val);
            if (-3 <= x && x <= 3) {
                return new ValidateResponse(true);
            } else {
                return new ValidateResponse(false, "X должен принадлежать [-3, 3]!");
            }

        } catch (NumberFormatException e) {
            return new ValidateResponse(false, "X должен являться числом!");
        }
    }

    private static ValidateResponse validateY(String val) {
        double y;
//        double[] yValues = {-5, -4, -3, -2, -1, 0, 1, 2, 3};
        boolean flag = false;
        try {
            y = Double.parseDouble(val);
//            for (double value : yValues) {
//                if (value == y) {
//                    flag = true;
//                    break;
//                }
//            }
            if (-5 <= y && y <= 3) {
                return new ValidateResponse(true);
            } else {
//                return new ValidateResponse(false, "Y должен принадлежать {-5, -4, -3, -2, -1, 0, 1, 2, 3}!");
                return new ValidateResponse(false, "Y должен принадлежать [-5; 3]!");

            }
        } catch (NumberFormatException e) {
            return new ValidateResponse(false, "Y должен являться числом!");
        }
    }

    private static ValidateResponse validateR(String val) {
        double r;
        try {
            r = Double.parseDouble(val);
            if (1 <= r && r <= 4) {
                return new ValidateResponse(true);
            } else {
                return new ValidateResponse(false, "R должен принадлежать [1, 4]!");
            }
        } catch (NumberFormatException e) {
            return new ValidateResponse(false, "R должен являться числом!");
        }
    }

}
