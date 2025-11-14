package utility;

import java.util.ResourceBundle;

public class CoordinatesValidator {

    public static ValidateResponse validate(String x, String y, String r, ResourceBundle messages) {
        ValidateResponse validatedX = validateX(x, messages);
        ValidateResponse validatedY = validateY(y, messages);
        ValidateResponse validatedR = validateR(r, messages);

        if (validatedX.isValid()) {

            if (validatedY.isValid()) {

                if (validatedR.isValid()) {
                    return new ValidateResponse(true);

                } else return validatedR;

            } else return validatedY;

        } else return validatedX;
    }

    private static ValidateResponse validateX(String val, ResourceBundle messages) throws NumberFormatException {
        double x;
        try {
            x = Double.parseDouble(val);
            if (-3 <= x && x <= 3) {
                return new ValidateResponse(true);
            } else {
                return new ValidateResponse(false, messages.getString("x_must_be_in_range"));
            }

        } catch (NumberFormatException e) {
            return new ValidateResponse(false, messages.getString("x_must_be_number"));
        }
    }

    private static ValidateResponse validateY(String val, ResourceBundle messages) {
        double y;
        boolean flag = false;
        try {
            y = Double.parseDouble(val);
            if (-5 <= y && y <= 3) {
                return new ValidateResponse(true);
            } else {
                return new ValidateResponse(false, messages.getString("y_must_be_in_range"));
            }
        } catch (NumberFormatException e) {
            return new ValidateResponse(false, messages.getString("y_must_be_number"));
        }
    }

    private static ValidateResponse validateR(String val, ResourceBundle messages) {
        double r;
        try {
            r = Double.parseDouble(val);
            if (1 <= r && r <= 4) {
                return new ValidateResponse(true);
            } else {
                return new ValidateResponse(false, messages.getString("r_must_be_in_range"));
            }
        } catch (NumberFormatException e) {
            return new ValidateResponse(false, messages.getString("r_must_be_number"));
        }
    }
}