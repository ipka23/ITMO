package server_commands;

import common_entities.MusicBand;
import common_utility.localization.LanguageManager;
import common_utility.network.Request;
import common_utility.network.Response;
import server_managers.CollectionManager;
import server_utility.Command;
import server_utility.RCommand;
import server_utility.consoles.ClientConsole;
import server_utility.interfaces.Console;
import server_utility.multithreading.Refresher;

import java.awt.*;
import java.io.IOException;
import java.util.Collection;

/**
 * Данный класс отвечает за выполнение команды "remove_by_id"
 *
 * @author ipka23
 */
public class Remove extends RCommand {
    private final ClientConsole console;
    private final CollectionManager collectionManager;

    /**
     * Конструктор
     *
     * @param console           интерфейс Console для взаимодействия с консолью
     * @param collectionManager объект CollectionManager для управления коллекцией
     */
    public Remove(ClientConsole console, CollectionManager collectionManager) {
        super(LanguageManager.getBundle().getString("remove"), LanguageManager.getBundle().getString("removeDescription"));
        this.console = console;
        this.collectionManager = collectionManager;
    }

    public Response execute(String[] command) {
        return null;
    }

    @Override
    public Response execute(String[] command, Request request) throws IOException, ClassNotFoundException {
        MusicBand band = request.getMusicBand();
        collectionManager.remove(band);
        Collection<MusicBand> collection = collectionManager.getCollection();
        Refresher.deleteRefresh(collection);
        return new Response(true, collectionManager.getString("bandRemoved"), collection);
    }
}