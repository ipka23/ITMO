package common_utility.exceptions;

public class ExitClientException extends RuntimeException {
    public ExitClientException() {
        super("Завершение работы клиента...");
    }
}
