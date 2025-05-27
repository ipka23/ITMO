package server_commands;

import common_entities.MusicBand;
import common_utility.localization.LanguageManager;
import common_utility.network.Request;
import common_utility.network.Response;
import server_managers.CollectionManager;
import server_utility.Command;
import server_utility.RCommand;
import server_utility.consoles.ClientConsole;
import server_utility.database.StatementValue;
import server_utility.exceptions.InputBreakException;
import server_utility.multithreading.Refresher;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * Данный класс отвечает за выполнение команды "update"
 *
 * @author ipka23
 */
public class Update extends RCommand {
    private final ClientConsole console;
    private final CollectionManager collectionManager;
    private final Add add;
    private ObjectInputStream inFromClient;
    private ObjectOutputStream outToClient;

    /**
     * Конструктор
     *
     * @param console           интерфейс Console для взаимодействия с консолью
     * @param collectionManager объект CollectionManager для управления коллекцией
     */
    public Update(ClientConsole console, CollectionManager collectionManager, ObjectInputStream inFromClient, ObjectOutputStream outToClient) {
        super(LanguageManager.getBundle().getString("update"), LanguageManager.getBundle().getString("updateDescription"));
        this.console = console;
        this.collectionManager = collectionManager;
        this.add = new Add(console, collectionManager, inFromClient, outToClient);
        this.inFromClient = inFromClient;
        this.outToClient = outToClient;
    }

    @Override
    public Response execute(String[] command, Request request) {
        MusicBand oldBand = request.getOldBand();
        long id = oldBand.getId();
        MusicBand newBand = request.getNewBand();

        newBand.setId(id);
        newBand.setCreationDate(oldBand.getCreationDate());

        collectionManager.removeByID(id);
        collectionManager.addMusicBand(newBand);

        collectionManager.addMusicBand(newBand);
        Collection<MusicBand> collection;
        try {
            collectionManager.getDatabaseManager().updateDB(newBand, id);
            collection = collectionManager.getCollection();
            Refresher.updateRefresh(oldBand, newBand, collection);
        } catch (InputBreakException e) {
            return new Response(false, collectionManager.getString("error"));
        }
        return new Response(true, collectionManager.getString("bandUpdated"), collection);
    }

    @Override
    public Response execute(String[] command) throws IOException, ClassNotFoundException {
        return null;
    }
}