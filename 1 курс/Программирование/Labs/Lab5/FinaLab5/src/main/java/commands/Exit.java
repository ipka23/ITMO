package commands;

import utility.Command;
import utility.exceptions.ExitException;
import utility.interfaces.Console;
import utility.ExecutionResponse;

/**
 * Данный класс отвечает за выполнение команды "exit"
 *
 * @author ipka23
 */
public class Exit extends Command {
    /**
     * Конструктор
     */
    public Exit() {
        super("exit", "завершить программу (без сохранения в файл)");
    }

    /**
     * Метод для выполнения команды
     *
     * @param command команда введенная пользователем
     * @return объект ExecutionResponse, содержащий результат выполнения команды
     */
    @Override
    public ExecutionResponse execute(String[] command) {
        throw new ExitException();
    }
}