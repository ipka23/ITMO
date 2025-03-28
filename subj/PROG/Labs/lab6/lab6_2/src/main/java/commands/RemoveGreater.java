package commands;

import entities.MusicBand;
import managers.CollectionManager;
import utility.Command;
import utility.ExecutionResponse;
import utility.exceptions.InputBreakException;
import utility.interfaces.Console;

import java.util.Collection;
import java.util.Iterator;

/**
 * Данный класс отвечает за выполнение команды "remove_greater"
 *
 * @author ipka23
 */
public class RemoveGreater extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    private final Add add;

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
        this.add = new Add(console, collectionManager);
    }

    /**
     * Метод для выполнения команды
     *
     * @param command команда введенная пользователем
     * @return объект ExecutionResponse, содержащий результат выполнения команды
     */
    public ExecutionResponse execute(String[] command) {
        if (!command[1].trim().isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
        Collection<MusicBand> collection = collectionManager.getCollection();

        try {
            MusicBand newBand = add.inputMusicBand();
            Iterator<MusicBand> iterator = collection.iterator();
            if (collection.isEmpty()) return new ExecutionResponse(false, "Коллекция пуста!");
            while (iterator.hasNext()) {
                if (iterator.next().getSales() > newBand.getSales()) {
                    iterator.remove();
                }
            }
            return new ExecutionResponse(true, "Из коллекции были удалены все элементы превышающие данный по параметру album.sales");
        } catch (InputBreakException e) {
            return new ExecutionResponse(false, e.getMessage());
        }
    }
}