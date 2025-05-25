package server_commands;

import common_entities.MusicBand;
import common_utility.localization.LanguageManager;
import common_utility.network.Response;
import server_managers.CollectionManager;
import server_utility.Command;
import server_utility.interfaces.Console;
import server_utility.multithreading.Refresher;

import java.awt.*;
import java.util.Collection;

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
        super(LanguageManager.getBundle().getString("remove"), LanguageManager.getBundle().getString("removeDescription"));
        this.console = console;
        this.collectionManager = collectionManager;
    }


    public Response execute(String[] command) {
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
        if (!current_user.equals(owner)) {
            return new Response(false, "Вы не можете удалить музыкальную группу с id = " + id + " т.к. не являетесь ее владельцем!");
        } else {
            collectionManager.removeByID(id);
            Collection<MusicBand> collection = collectionManager.getCollection();
            Refresher.deleteRefresh(collection);
            return new Response(true, "Музыкальная группа с id = " + id + " была удалена из коллекции!", collection);
        }
    }
}