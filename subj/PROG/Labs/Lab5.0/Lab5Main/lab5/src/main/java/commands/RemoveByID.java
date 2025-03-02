package commands;

import managers.CollectionManager;
import utility.Console;
import utility.ExecutionResponse;


public class RemoveByID extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public RemoveByID(Console console, CollectionManager collectionManager) {
        super("remove_by_id id", "удалить элемент из коллекции по его id");
        this.console = console;
        this.collectionManager = collectionManager;
    }

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
        var band = collectionManager.getMusicBandById(id);
        if (band == null || !collectionManager.getCollection().contains(band)) {
            return new ExecutionResponse(false, "В коллекции нет элемента с таким id!");
        }
        collectionManager.removeByID(id);
        return new ExecutionResponse(true, "Элемент с id = " + id + " был удален из коллекции!");
    }
}