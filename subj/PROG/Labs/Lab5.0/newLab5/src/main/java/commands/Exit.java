package commands;

import utility.Console;
import utility.ExecutionResponse;

/**
 * Данный класс отвечает за выполнение команды "exit"
 *
 * @author ipka23
 */
public class Exit extends Command {
    private final Console CONSOLE;
    /**
     * Конструктор
     *
     * @param console интерфейс Console для взаимодействия с консолью
     */
    public Exit(Console console) {
        super("exit", "завершить программу (без сохранения в файл)");
        this.CONSOLE = console;
    }

    /**
     * Метод для выполнения команды
     *
     * @param args аргументы команды
     * @return объект ExecutionResponse, содержащий результат выполнения команды
     */
    @Override
    public ExecutionResponse execute(String[] args) {
        if (!args[1].trim().isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
        return new ExecutionResponse(true);
    }
}