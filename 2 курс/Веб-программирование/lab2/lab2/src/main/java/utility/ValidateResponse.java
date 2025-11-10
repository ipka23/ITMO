package utility;

import lombok.Getter;

@Getter
public class ValidateResponse {
    private boolean valid;
    private String message;

    public ValidateResponse(boolean status, String message) {
        valid = status;
        this.message = message;
    }
    public ValidateResponse(boolean status) {
        valid = status;
        this.message = "успешно";
    }
}