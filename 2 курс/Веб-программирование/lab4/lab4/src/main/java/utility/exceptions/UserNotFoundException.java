package utility.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userId) {
       super("Пользователь с ID " + userId + " не найден!");
    }
}
