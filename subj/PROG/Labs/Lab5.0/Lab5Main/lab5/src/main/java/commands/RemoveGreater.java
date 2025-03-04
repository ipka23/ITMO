package commands;

import managers.CollectionManager;
import models.Ask;
import models.MusicBand;
import utility.Console;
import utility.ExecutionResponse;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Данный класс отвечает за выполнение команды "remove_greater"
 *
 * @author ipka23
 */
public class RemoveGreater extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    /**
     * Конструктор
     *
     * @param console           интерфейс Console для взаимодействия с консолью
     * @param collectionManager объект CollectionManager для управления коллекцией
     */
    public RemoveGreater(Console console, CollectionManager collectionManager) {
        super("remove_greater", "удалить из коллекции все элементы, превышающие заданный");
        this.console = console;
        this.collectionManager = collectionManager;
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
        HashSet<MusicBand> collection = collectionManager.getCollection();

        try {
            MusicBand newBand = Ask.askMusicBand(console, collectionManager.getFreeId());
            Iterator<MusicBand> iterator = collection.iterator();
            if (collection.isEmpty()) return new ExecutionResponse(true, "Коллекция пуста!");
            while (iterator.hasNext()) {
                if (iterator.next().getSales() > newBand.getSales()) {
                    iterator.remove();
                }
            }
            return new ExecutionResponse(true, "Из коллекции были удалены все элементы превышающие данный по параметру album.sales");
        } catch (Ask.AskBreak e) {
            return new ExecutionResponse(true, "Отмена ввода...");
        }
    }
}
