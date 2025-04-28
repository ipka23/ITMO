package server_commands;

import common_utility.network.Response;
import server_managers.CollectionManager;
import server_utility.Command;
import server_utility.interfaces.Console;

/**
 * Данный класс отвечает за выполнение команды "remove_by_id"
 *
 * @author ipka23
 */
public class RemoveByID extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    /**
     * Конструктор
     *
     * @param console           интерфейс Console для взаимодействия с консолью
     * @param collectionManager объект CollectionManager для управления коллекцией
     */
    public RemoveByID(Console console, CollectionManager collectionManager) {
        super("remove id", "удалить элемент из коллекции по его id");
        this.console = console;
        this.collectionManager = collectionManager;
    }


    public Response execute(String[] command) {
        if (command[1].trim().isEmpty()) {
            return new Response(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
        }
        long id;
        try {
            id = Long.parseLong(command[1].trim());
        } catch (NumberFormatException e) {
            return new Response(false, "Неверный формат id!");
        }
        var band = collectionManager.getMusicBandById(id);
        if (band == null || !collectionManager.getCollection().contains(band)) {
            return new Response(false, "В коллекции нет музыкальной группы с таким id!");
        }
        collectionManager.removeByID(id);
        return new Response(false, "Музыкальная группа с id = " + id + " была удалена из коллекции!");
    }
}