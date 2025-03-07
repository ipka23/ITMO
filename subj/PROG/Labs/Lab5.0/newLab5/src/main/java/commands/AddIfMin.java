package commands;

import utility.exceptions.AddBreak;
import managers.CollectionManager;
import entities.MusicBand;
import utility.Command;
import utility.interfaces.Console;
import utility.ExecutionResponse;

/**
 * Данный класс отвечает за выполнение команды "add_if_min"
 *
 * @author ipka23
 */
public class AddIfMin extends Command {
    private final Console CONSOLE;
    private final CollectionManager COLLECTION_MANAGER;

    /**
     * Конструктор
     *
     * @param console           интерфейс Console для взаимодействия с консолью
     * @param collectionManager объект CollectionManager для управления коллекцией
     */
    public AddIfMin(Console console, CollectionManager collectionManager) {
        super("add_if_min", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
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
        try {
            if (!args[1].isEmpty())
                return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
            long id = COLLECTION_MANAGER.getId();
            MusicBand newBand = Add.inputMusicBand(CONSOLE, id);
            if (newBand.getSales() < COLLECTION_MANAGER.getMin().getSales()) {
                COLLECTION_MANAGER.add(newBand);
                return new ExecutionResponse(true, "В коллекцию был добавлен элемент album.sales которого меньше чем у элемента с минимальным album.sales!");
            } else {
                COLLECTION_MANAGER.removeByID(id);
                return new ExecutionResponse(true, "Элемент не был добавлен в коллекцию, т. к. его album.sales превышают элемент с минимальным album.sales!");
            }
        } catch (AddBreak e) {
            return new ExecutionResponse(true, e.getMessage());
        }
    }
}
