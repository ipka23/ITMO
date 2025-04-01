package server_commands;

import common_entities.MusicBand;
import common_utility.network.ExecutionResponse;
import server_managers.CollectionManager;
import server_utility.Command;
import server_utility.exceptions.InputBreakException;
import server_utility.interfaces.Console;

import java.io.IOException;

/**
 * Данный класс отвечает за выполнение команды "add_if_min"
 *
 * @author ipka23
 */
public class AddIfMin extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    private final Add add;

    /**
     * Конструктор
     *
     * @param console           интерфейс Console для взаимодействия с консолью
     * @param collectionManager объект CollectionManager для управления коллекцией
     */
    public AddIfMin(Console console, CollectionManager collectionManager) {
        super("add_if_min", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
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
            if (!command[1].isEmpty())
                return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
            long id = collectionManager.getFreeId();
            MusicBand newBand = add.inputMusicBand();
            if (newBand.getSales() < collectionManager.getMin().getSales()) {
                collectionManager.addMusicBand(newBand);
                return new ExecutionResponse(false, "В коллекцию был добавлен элемент album.sales которого меньше чем у элемента с минимальным album.sales!");
            } else {
                collectionManager.removeByID(id);
                return new ExecutionResponse(false, "Элемент не был добавлен в коллекцию, т. к. его album.sales превышают элемент с минимальным album.sales!");
            }
        } catch (InputBreakException e) {
            return new ExecutionResponse(true, e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}