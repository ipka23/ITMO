package server_commands;

import common_entities.MusicBand;
import common_utility.localization.LanguageManager;
import common_utility.network.Request;
import common_utility.network.Response;
import server_managers.CollectionManager;
import server_managers.DatabaseManager;
import server_utility.Command;
import server_utility.RCommand;
import server_utility.consoles.ClientConsole;
import server_utility.database.StatementValue;
import server_utility.exceptions.InputBreakException;
import server_utility.multithreading.Refresher;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;

/**
 * Данный класс отвечает за выполнение команды "remove_greater"
 *
 * @author ipka23
 */
public class RemoveGreater extends RCommand {
    private final ClientConsole console;
    private final CollectionManager collectionManager;
    private final Add add;

    /**
     * Конструктор
     *
     * @param console           интерфейс Console для взаимодействия с консолью
     * @param collectionManager объект CollectionManager для управления коллекцией
     */
    public RemoveGreater(ClientConsole console, CollectionManager collectionManager, ObjectInputStream inFromClient, ObjectOutputStream outToClient) {
        super(LanguageManager.getBundle().getString("remove_greater"), LanguageManager.getBundle().getString("remove_greaterDescription"));
        this.console = console;
        this.collectionManager = collectionManager;
        this.add = new Add(console, collectionManager, inFromClient, outToClient);
    }

    @Override
    public Response execute(String[] command) throws IOException, ClassNotFoundException {
        return null;
    }
    @Override
    public Response execute(String[] command, Request request) {
        Collection<MusicBand> collection = collectionManager.getCollection();
        DatabaseManager dbManager = collectionManager.getDatabaseManager();
        Connection connection = dbManager.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(StatementValue.REMOVE_BANDS.toString())) {
            MusicBand newBand = add.getBandFromRequest(request);
            newBand.setCreationDate(LocalDate.now());
            if(!newBand.validate()) {
                return new Response(false, collectionManager.getString("validationError"));
            }
            String owner = newBand.getOwner();
            Response response = collectionManager.addMusicBand(newBand);
            response.setExitStatus(true);
            ps.setString(1, owner);
            ps.setDouble(2, newBand.getBestAlbum().getSales());
            ps.executeUpdate();

            dbManager.loadCollectionFromDB();
            Collection<MusicBand> collection1 = collectionManager.getCollection();
            Refresher.deleteRefresh(collection1);
            return new Response(true, collectionManager.getString("removeGreaterMessage"), collectionManager.getCollection());
        } catch (InputBreakException | IOException | ClassNotFoundException e) {
            return new Response(false, collectionManager.getString("error"));
        } catch (SQLException e) {
            return new Response(false, collectionManager.getString("error") + ": " + e.getMessage());
        }
    }

}