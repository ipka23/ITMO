package server_commands;

import common_entities.MusicBand;
import common_utility.network.Response;
import server_managers.CollectionManager;
import server_managers.DatabaseManager;
import server_utility.Command;
import server_utility.database.StatementValue;
import server_utility.interfaces.Console;

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
public class Clear extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    /**
     * Конструктор
     *
     * @param console           интерфейс Console для взаимодействия с консолью
     * @param collectionManager объект CollectionManager для управления коллекцией
     */
    public Clear(Console console, CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }


    @Override
    public Response execute(String[] command) {
        if (!command[1].trim().isEmpty())
            return new Response(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
        Collection<MusicBand> collection = collectionManager.getCollection();
        if (collection.isEmpty()) return new Response(false, "Коллекция пуста!");
        DatabaseManager dbManager = collectionManager.getDatabaseManager();
        Connection connection = dbManager.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(StatementValue.REMOVE_BANDS.toString())){
            ps.setString(1, dbManager.getUser().getUsername());
            ps.setDouble(2, -1);
            ps.executeUpdate();
        } catch (SQLException e) {
            return new Response(false, "Ошибка: " + e.getMessage());
        }
        dbManager.loadCollectionFromDB();
        return new Response(false, "Все банды принадлежащие вам были удалены из коллекции!", collectionManager.getCollection());
    }
}