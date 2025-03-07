package commands;

import managers.CollectionManager;
import entities.MusicBand;
import utility.Command;
import utility.interfaces.Console;
import utility.ExecutionResponse;

/**
 * Данный класс отвечает за выполнение команды "max_by_best_album"
 *
 * @author ipka23
 */
public class MaxByBestBestAlbum extends Command {
    private final Console CONSOLE;
    private final CollectionManager COLLECTION_MANAGER;

    /**
     * Конструктор
     *
     * @param console           интерфейс Console для взаимодействия с консолью
     * @param collectionManager объект CollectionManager для управления коллекцией
     */
    public MaxByBestBestAlbum(Console console, CollectionManager collectionManager) {
        super("max_by_best_album", "вывести любой объект из коллекции, значение поля bestAlbum которого является максимальным");
        this.CONSOLE = console;
        this.COLLECTION_MANAGER = collectionManager;
    }

    /**
     * Метод для выполнения команды
     *
     * @param args аргументы команды
     * @return объект ExecutionResponse, содержащий результат выполнения команды
     */
    @Override
    public ExecutionResponse execute(String[] args) {
        if (!args[1].trim().isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
        MusicBand bestBand;
        bestBand = COLLECTION_MANAGER.getMax();
        StringBuilder s = new StringBuilder();
        s.append("MusicBand с максимальным количеством album.sales:").append("\n");
        s.append(bestBand.toString());
        return new ExecutionResponse(true, s.toString());
    }
}
