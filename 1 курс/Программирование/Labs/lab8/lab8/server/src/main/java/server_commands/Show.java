package server_commands;

import common_entities.MusicBand;
import common_utility.network.Response;
import server_managers.CollectionManager;
import server_utility.Command;
import server_utility.interfaces.Console;

import java.util.Collection;
import java.util.Comparator;

/**
 * Данный класс отвечает за выполнение команды "show"
 *
 * @author ipka23
 */
public class Show extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    /**
     * Конструктор
     *
     * @param console           интерфейс Console для взаимодействия с консолью
     * @param collectionManager объект CollectionManager для управления коллекцией
     */
    public Show(Console console, CollectionManager collectionManager) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String[] command) {
        if (!command[1].trim().isEmpty())
            return new Response(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
        collectionManager.getDatabaseManager().loadCollectionFromDB();
        Collection<MusicBand> collection = collectionManager.getCollection();
        collection = collection.stream().sorted(Comparator.comparing(MusicBand::getId)).toList();
        return new Response(false, collectionManager.toString(), collection);
    }
}