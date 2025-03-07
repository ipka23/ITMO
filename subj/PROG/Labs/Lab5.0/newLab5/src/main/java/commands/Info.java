package commands;

import managers.CollectionManager;
import utility.Command;
import utility.interfaces.Console;
import utility.ExecutionResponse;

/**
 * Данный класс отвечает за выполнение команды "info"
 *
 * @author ipka23
 */
public class Info extends Command {
    private final Console CONSOLE;
    private final CollectionManager COLLECTION_MANAGER;

    /**
     * Конструктор
     *
     * @param console           интерфейс Console для взаимодействия с консолью
     * @param collectionManager объект CollectionManager для управления коллекцией
     */
    public Info(Console console, CollectionManager collectionManager) {
        super("info", "вывести в стандартный поток вывода информацию о коллекции");
        this.CONSOLE = console;
        this.COLLECTION_MANAGER = collectionManager;
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
        return new ExecutionResponse(true, COLLECTION_MANAGER.info());
    }
}
