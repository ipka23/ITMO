package server_utility.exceptions;

public class RecursionDepthExceedException extends RuntimeException {
    public RecursionDepthExceedException() {
        super("Превышена допустимая глубина рекурсии!");
    }
}
