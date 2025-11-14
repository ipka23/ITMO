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
                return new ValidateResponse(false, Localizer.getBundle().getString("x_must_be_in_range"));
            }

        } catch (NumberFormatException e) {
            return new ValidateResponse(false, Localizer.getBundle().getString("x_must_be_number"));
        }
    }

    private static ValidateResponse validateY(String val) {
        double y;
        boolean flag = false;
        try {
            y = Double.parseDouble(val);
            if (-5 <= y && y <= 3) {
                return new ValidateResponse(true);
            } else {
                return new ValidateResponse(false, Localizer.getBundle().getString("y_must_be_in_range"));
            }
        } catch (NumberFormatException e) {
            return new ValidateResponse(false, Localizer.getBundle().getString("y_must_be_number"));
        }
    }

    private static ValidateResponse validateR(String val) {
        double r;
        try {
            r = Double.parseDouble(val);
            if (1 <= r && r <= 4) {
                return new ValidateResponse(true);
            } else {
                return new ValidateResponse(false, Localizer.getBundle().getString("r_must_be_in_range"));
            }
        } catch (NumberFormatException e) {
            return new ValidateResponse(false, Localizer.getBundle().getString("r_must_be_number"));
        }
    }
}