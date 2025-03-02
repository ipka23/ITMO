package utility;

public class ExecutionResponse {
    boolean exitStatus;
    String message;

    public ExecutionResponse(boolean exitStatus, String message) {
        this.exitStatus = exitStatus;
        this.message = message;
    }

    public ExecutionResponse(boolean exitStatus) {
        this.exitStatus = exitStatus;
    }

    public boolean getExitStatus() {
        return exitStatus;
    }
    public String getMessage() {
        return message;
    }
}