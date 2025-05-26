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
import server_utility.interfaces.Console;
import server_utility.multithreading.Refresher;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Данный класс отвечает за выполнение команды "clear"
 *
 * @author ipka23
 */
public class Clear extends RCommand {
    private final ClientConsole console;
    private final CollectionManager collectionManager;

    /**
     * Конструктор
     *
     * @param console           интерфейс Console для взаимодействия с консолью
     * @param collectionManager объект CollectionManager для управления коллекцией
     */
    public Clear(ClientConsole console, CollectionManager collectionManager) {
        super(LanguageManager.getBundle().getString("clear"), LanguageManager.getBundle().getString("clearDescription"));
        this.console = console;
        this.collectionManager = collectionManager;
    }


    @Override
    public Response execute(String[] command) {
        return null;
    }

    @Override
    public Response execute(String[] command, Request request) throws IOException, ClassNotFoundException {
        DatabaseManager dbManager = collectionManager.getDatabaseManager();
        Connection connection = dbManager.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(StatementValue.REMOVE_BANDS.toString())){
            ps.setString(1, request.getUser().getUsername());
            ps.setDouble(2, -1);
            ps.executeUpdate();
        } catch (SQLException e) {
            return new Response(false, collectionManager.getString("error"));
        }
        dbManager.loadCollectionFromDB();
        Collection<MusicBand> collection = collectionManager.getCollection();
        Refresher.deleteRefresh(collection);
        return new Response(true, "Все банды принадлежащие вам были удалены из коллекции!", collection);
    }
}