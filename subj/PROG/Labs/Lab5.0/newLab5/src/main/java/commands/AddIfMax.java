package commands;

import exceptions.AddBreak;
import managers.CollectionManager;
import models.MusicBand;
import utility.Console;
import utility.ExecutionResponse;

/**
 * Данный класс отвечает за выполнение команды "add_if_max"
 *
 * @author ipka23
 */
public class AddIfMax extends Command {
    private final Console CONSOLE;
    private final CollectionManager COLLECTION_MANAGER;

    /**
     * Конструктор
     *
     * @param console           интерфейс Console для взаимодействия с консолью
     * @param collectionManager объект CollectionManager для управления коллекцией
     */
    public AddIfMax(Console console, CollectionManager collectionManager) {
        super("add_if_max", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
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
            if (!args[1].trim().isEmpty())
                return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
            long id = COLLECTION_MANAGER.getId();
            MusicBand newBand = Add.inputMusicBand(CONSOLE, id);
            if (newBand.getSales() > COLLECTION_MANAGER.getMax().getSales()) {
                COLLECTION_MANAGER.add(newBand);
                return new ExecutionResponse(true, "В коллекцию был добавлен элемент album.sales которого превышают элемент с максимальным album.sales!");
            } else {
                COLLECTION_MANAGER.removeByID(id);
                return new ExecutionResponse(true, "Элемент не был добавлен в коллекцию, т. к. его album.sales не превышают элемент с максимальным album.sales!");
            }
        } catch (AddBreak e) {
            return new ExecutionResponse(true, e.getMessage());
        }
    }
}
