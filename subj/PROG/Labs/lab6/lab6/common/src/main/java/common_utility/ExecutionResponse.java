package common_utility;

import java.io.Serializable;

public class ExecutionResponse implements Serializable {
    private boolean isCompleted;
    private String message;
    public ExecutionResponse(boolean isCompleted, String message) {
        this.isCompleted = isCompleted;
        this.message = message;
    }
    public ExecutionResponse(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
    public boolean isCompleted() {
        return isCompleted;
    }


    public String getMessage() {
        return message;
    }
    @Override
    public String toString() {
        return message;
    }
}
