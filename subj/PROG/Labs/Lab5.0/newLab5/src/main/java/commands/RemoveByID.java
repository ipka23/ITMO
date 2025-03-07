package commands;

import managers.CollectionManager;
import utility.Command;
import utility.interfaces.Console;
import utility.ExecutionResponse;

/**
 * Данный класс отвечает за выполнение команды "remove_by_id"
 *
 * @author ipka23
 */
public class RemoveByID extends Command {
    private final Console CONSOLE;
    private final CollectionManager COLLECTION_MANAGER;

    /**
     * Конструктор
     *
     * @param console           интерфейс Console для взаимодействия с консолью
     * @param collectionManager объект CollectionManager для управления коллекцией
     */
    public RemoveByID(Console console, CollectionManager collectionManager) {
        super("remove_by_id id", "удалить элемент из коллекции по его id");
        this.CONSOLE = console;
        this.COLLECTION_MANAGER = collectionManager;
    }

    /**
     * Метод для выполнения команды
     *
     * @param args аргументы команды
     * @return объект ExecutionResponse, содержащий результат выполнения команды
     */
    public ExecutionResponse execute(String[] args) {
        if (args[1].trim().isEmpty()) {
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
        }
        long id;
        try {
            id = Long.parseLong(args[1].trim());
        } catch (NumberFormatException e) {
            return new ExecutionResponse(false, "Неверный формат id!");
        }
        var band = COLLECTION_MANAGER.getMusicBandById(id);
        if (band == null || !COLLECTION_MANAGER.getMusicBands().contains(band)) {
            return new ExecutionResponse(false, "В коллекции нет элемента с таким id!");
        }
        COLLECTION_MANAGER.removeByID(id);
        return new ExecutionResponse(true, "Элемент с id = " + id + " был удален из коллекции!");
    }
}