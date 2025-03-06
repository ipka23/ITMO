package exceptions;

public class ValidateException extends Exception {
  public ValidateException() {
    super("Validation Error: проверьте валидность полей!" );
  }
}
