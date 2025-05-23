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
public class Remove extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    /**
     * Конструктор
     *
     * @param console           интерфейс Console для взаимодействия с консолью
     * @param collectionManager объект CollectionManager для управления коллекцией
     */
    public Remove(Console console, CollectionManager collectionManager) {
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
        String current_user = collectionManager.getDatabaseManager().getUser().getUsername();
        String owner = band.getOwner();
        if (!current_user.equals(owner)){
            return new Response(false, "Вы не можете удалить музыкальную группу с id = " + id + " т.к. не являетесь ее владельцем!");
        }
        collectionManager.removeByID(id);
        return new Response(false, "Музыкальная группа с id = " + id + " была удалена из коллекции!");
    }
}