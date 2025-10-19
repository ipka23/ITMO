public class ValidateResponse {
    private boolean status;
    private String message;

    public boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public ValidateResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
    }
    public ValidateResponse(boolean status) {
        this.status = status;
        this.message = "успешно";
    }
}