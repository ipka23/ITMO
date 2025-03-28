package utility.exceptions;

public class ExitException extends RuntimeException {
    public ExitException() {
        super("Завершение работы с консолью...");
    }
}
