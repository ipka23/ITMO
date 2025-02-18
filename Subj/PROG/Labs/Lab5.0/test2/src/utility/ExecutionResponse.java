package utility;

public class ExecutionResponse {
    boolean exitStatus;
    String message;

    public ExecutionResponse(boolean exitStatus, String message) {
        this.exitStatus = exitStatus;
        this.message = message;
    }

    public boolean getExitCode() {
        return exitStatus;
    }
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return String.valueOf(exitStatus)+";"+message;
    }
}