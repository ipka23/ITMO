package commands;

import managers.CollectionManager;
import utility.Command;
import utility.ExecutionResponse;
import utility.interfaces.Console;

/**
 * Данный класс отвечает за выполнение команды "clear"
 *
 * @author ipka23
 */
public class Clear extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    /**
     * Конструктор
     *
     * @param console           интерфейс Console для взаимодействия с консолью
     * @param collectionManager объект CollectionManager для управления коллекцией
     */
    public Clear(Console console, CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Метод для выполнения команды
     *
     * @param command команда введенная пользователем
     * @return объект ExecutionResponse, содержащий результат выполнения команды
     */
    @Override
    public ExecutionResponse execute(String[] command) {
        if (!command[1].trim().isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
        collectionManager.getCollection().clear();
        return new ExecutionResponse(true, "Коллекция была очищена!");
    }
}