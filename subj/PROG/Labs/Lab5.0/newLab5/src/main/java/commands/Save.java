package commands;

import managers.CollectionManager;
import utility.Command;
import utility.interfaces.Console;
import utility.ExecutionResponse;

/**
 * Данный класс отвечает за выполнение команды "save"
 *
 * @author ipka23
 */
public class Save extends Command {
    private final Console CONSOLE;
    private final CollectionManager COLLECTION_MANAGER;

    /**
     * Конструктор
     *
     * @param console           интерфейс Console для взаимодействия с консолью
     * @param collectionManager объект CollectionManager для управления коллекцией
     */
    public Save(Console console, CollectionManager collectionManager) {
        super("save", "сохранить коллекцию в файл");
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
        COLLECTION_MANAGER.saveCollectionToFile();
        return new ExecutionResponse(true, "Коллекция была сохранена!");
    }
}
