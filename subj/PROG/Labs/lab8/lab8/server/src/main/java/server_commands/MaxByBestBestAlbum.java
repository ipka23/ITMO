package server_commands;

import common_entities.MusicBand;
import common_utility.network.Response;
import server_managers.CollectionManager;
import server_utility.Command;
import server_utility.interfaces.Console;

import java.util.Collection;
import java.util.HashSet;

/**
 * Данный класс отвечает за выполнение команды "max_by_best_album"
 *
 * @author ipka23
 */
public class MaxByBestBestAlbum extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    /**
     * Конструктор
     *
     * @param console           интерфейс Console для взаимодействия с консолью
     * @param collectionManager объект CollectionManager для управления коллекцией
     */
    public MaxByBestBestAlbum(Console console, CollectionManager collectionManager) {
        super("max_by_best_album", "вывести любой объект из коллекции, значение поля bestAlbum которого является максимальным");
        this.console = console;
        this.collectionManager= collectionManager;
    }

    @Override
    public Response execute(String[] command) {
        if (!command[1].trim().isEmpty())
            return new Response(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
        MusicBand bestBand;
        bestBand = collectionManager.getMax();
        Collection<MusicBand> newCollection = new HashSet<>();
        newCollection.add(bestBand);
        return new Response(false, "max_by_best_album", newCollection);
    }
}