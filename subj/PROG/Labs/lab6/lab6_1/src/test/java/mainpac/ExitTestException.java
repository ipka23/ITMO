package mainpac;

public class ExitTestException extends RuntimeException {
    public ExitTestException() {
        super("User exited...");
    }
}
