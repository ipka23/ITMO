package utility;

public class ExecutionResponse {
    private boolean isCompleted;
    private String message;
    public ExecutionResponse(boolean isCompleted, String message) {
        this.isCompleted = isCompleted;
        this.message = message;
    }
    public boolean isCompleted() {
        return isCompleted;
    }

    public boolean isSuccess() {
        return isCompleted;
    }
    public String getMessage() {
        return message + "\n";
    }

}
