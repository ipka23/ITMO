package server_commands;

import common_entities.MusicBand;
import server_managers.CollectionManager;
import server_utility.Command;
import common_utility.ExecutionResponse;
import server_utility.exceptions.InputBreakException;
import server_utility.interfaces.Console;

/**
 * Данный класс отвечает за выполнение команды "add_if_max"
 *
 * @author ipka23
 */
public class AddIfMax extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    private final Add add;


    /**
     * Конструктор
     *
     * @param console           интерфейс Console для взаимодействия с консолью
     * @param collectionManager объект CollectionManager для управления коллекцией
     */
    public AddIfMax(Console console, CollectionManager collectionManager) {
        super("add_if_max", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
        this.add = new Add(console, collectionManager);
    }

    /**
     * Метод для выполнения команды
     *
     * @param command команда введенная пользователем
     * @return объект utility.ExecutionResponse, содержащий результат выполнения команды
     */
    @Override
    public ExecutionResponse execute(String[] command) {
        try {
            if (!command[1].trim().isEmpty())
                return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
            long id = collectionManager.getFreeId();

            MusicBand newBand = add.inputMusicBand();
            if (newBand.getSales() > collectionManager.getMax().getSales()) {
                collectionManager.addMusicBand(newBand);
                return new ExecutionResponse(true, "В коллекцию был добавлен элемент album.sales которого превышают элемент с максимальным album.sales!");
            } else {
                collectionManager.removeByID(id);
                return new ExecutionResponse(true, "Элемент не был добавлен в коллекцию, т. к. его album.sales не превышают элемент с максимальным album.sales!");
            }
        } catch (InputBreakException e) {
            return new ExecutionResponse(false, e.getMessage());
        }
    }
}