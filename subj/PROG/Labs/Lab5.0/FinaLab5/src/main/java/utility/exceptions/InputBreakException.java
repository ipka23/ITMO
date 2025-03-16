package utility.exceptions;

public class InputBreakException extends RuntimeException {
    public InputBreakException() {
        super("Отмена ввода...");
    }
}
