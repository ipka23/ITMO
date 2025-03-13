package utility.exceptions;

public class ValidateException extends RuntimeException {
    public ValidateException(String message) {
        super("Поля MusicBands не валидны!");
    }
}
