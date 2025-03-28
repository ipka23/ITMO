package commands;

import entities.MusicBand;
import managers.CollectionManager;
import utility.Command;
import utility.ExecutionResponse;
import utility.interfaces.Console;

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
     * @return объект ExecutionResponse, содержащий результат выполнения команды
     */
    @Override
    public ExecutionResponse execute(String[] command) {
        if (!command[1].trim().isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
        MusicBand bestBand;
        bestBand = collectionManager.getMax();
        StringBuilder message = new StringBuilder();
        message.append("MusicBand с максимальным количеством album.sales:").append("\n");
        message.append(bestBand.toString());
        return new ExecutionResponse(true, message.toString());
    }
}