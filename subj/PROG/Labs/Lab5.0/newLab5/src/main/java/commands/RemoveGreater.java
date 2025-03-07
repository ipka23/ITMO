package commands;

import utility.exceptions.AddBreak;
import managers.CollectionManager;
import entities.MusicBand;
import utility.Command;
import utility.interfaces.Console;
import utility.ExecutionResponse;

import java.util.Collection;
import java.util.Iterator;

/**
 * Данный класс отвечает за выполнение команды "remove_greater"
 *
 * @author ipka23
 */
public class RemoveGreater extends Command {
    private final Console CONSOLE;
    private final CollectionManager COLLECTION_MANAGER;

    /**
     * Конструктор
     *
     * @param console           интерфейс Console для взаимодействия с консолью
     * @param collectionManager объект CollectionManager для управления коллекцией
     */
    public RemoveGreater(Console console, CollectionManager collectionManager) {
        super("remove_greater", "удалить из коллекции все элементы, превышающие заданный");
        this.CONSOLE = console;
        this.COLLECTION_MANAGER = collectionManager;
    }

    /**
     * Метод для выполнения команды
     *
     * @param args аргументы команды
     * @return объект ExecutionResponse, содержащий результат выполнения команды
     */
    public ExecutionResponse execute(String[] args) {
        if (!args[1].trim().isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
        Collection<MusicBand> collection = COLLECTION_MANAGER.getMusicBands();

        try {
            MusicBand newBand = Add.inputMusicBand(CONSOLE, COLLECTION_MANAGER.getId());
            Iterator<MusicBand> iterator = collection.iterator();
            if (collection.isEmpty()) return new ExecutionResponse(true, "Коллекция пуста!");
            while (iterator.hasNext()) {
                if (iterator.next().getSales() > newBand.getSales()) {
                    iterator.remove();
                }
            }
            return new ExecutionResponse(true, "Из коллекции были удалены все элементы превышающие данный по параметру album.sales");
        } catch (AddBreak e) {
            return new ExecutionResponse(true, e.getMessage());
        }
    }
}
