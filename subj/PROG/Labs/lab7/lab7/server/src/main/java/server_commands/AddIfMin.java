package server_commands;

import common_entities.MusicBand;
import common_utility.network.Response;
import server_managers.CollectionManager;
import server_utility.Command;
import server_utility.consoles.ClientConsole;
import server_utility.exceptions.InputBreakException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Данный класс отвечает за выполнение команды "add_if_min"
 *
 * @author ipka23
 */
public class AddIfMin extends Command {
    private final ClientConsole console;
    private final CollectionManager collectionManager;
    private final Add add;

    /**
     * Конструктор
     *
     * @param console           интерфейс Console для взаимодействия с консолью
     * @param collectionManager объект CollectionManager для управления коллекцией
     */
    public AddIfMin(ClientConsole console, CollectionManager collectionManager, ObjectInputStream inFromClient, ObjectOutputStream outToClient) {
        super("add_if_min", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
        this.add = new Add(console, collectionManager, inFromClient, outToClient);
    }


    @Override
    public Response execute(String[] command) {
        try {
            if (!command[1].isEmpty())
                return new Response(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
            MusicBand newBand = add.inputMusicBand();
            return collectionManager.addMusicBandIfMin(newBand);
        } catch (InputBreakException e) {
            return new Response(true, e.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}