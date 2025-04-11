package server_commands;

import common_entities.MusicBand;
import common_utility.network.Response;
import server_managers.CollectionManager;
import server_utility.Command;
import server_utility.interfaces.Console;

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

    /**
     * Метод для выполнения команды
     *
     * @param command команда введенная пользователем
     * @return объект utility.ExecutionResponse, содержащий результат выполнения команды
     */
    @Override
    public Response execute(String[] command) {
        if (!command[1].trim().isEmpty())
            return new Response(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
        MusicBand bestBand;
        bestBand = collectionManager.getMax();
        StringBuilder message = new StringBuilder();
        message.append("MusicBand с максимальным количеством album.sales:").append("\n");
        message.append(bestBand.toString());
        return new Response(false, message.toString());
    }
}