package utility;

/**
 * Данный класс отвечает за вывод состояния выполнения команды.
 * Он содержит статус завершения и сообщение, описывающее результат выполнения команды.
 *
 * @author ipka23
 */
public class ExecutionResponse {
    boolean exitStatus;
    String message;

    /**
     * Конструктор класса ExecutionResponse.
     *
     * @param exitStatus статус завершения команды (true, если команда выполнена успешно, false в противном случае)
     * @param message    сообщение, описывающее результат выполнения команды
     */
    public ExecutionResponse(boolean exitStatus, String message) {
        this.exitStatus = exitStatus;
        this.message = message;
    }

    /**
     * Конструктор класса ExecutionResponse.
     *
     * @param exitStatus статус завершения команды (true, если команда выполнена успешно, false в противном случае)
     */
    public ExecutionResponse(boolean exitStatus) {
        this.exitStatus = exitStatus;
    }

    /**
     * Метод для возвращения статуса завершения команды
     *
     * @return true, если команда выполнена успешно, false в противном случае
     */
    public boolean getExitStatus() {
        return exitStatus;
    }

    /**
     * Метод для возвращения сообщения, описывающего результат выполнения команды
     *
     * @return сообщение о результате выполнения команды
     */
    public String getMessage() {
        return message;
    }
}