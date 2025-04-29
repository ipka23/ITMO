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
 * Данный класс отвечает за выполнение команды "add_if_max"
 *
 * @author ipka23
 */
public class AddIfMax extends Command {
    private final ClientConsole console;
    private final CollectionManager collectionManager;
    private final Add add;


    /**
     * Конструктор
     *
     * @param console           интерфейс Console для взаимодействия с консолью
     * @param collectionManager объект CollectionManager для управления коллекцией
     */
    public AddIfMax(ClientConsole console, CollectionManager collectionManager, ObjectInputStream inFromClient, ObjectOutputStream outToClient)  {
        super("add_if_max", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
        this.add = new Add(console, collectionManager, inFromClient, outToClient);
    }


    @Override
    public Response execute(String[] command) {
        try {
            if (!command[1].trim().isEmpty())
                return new Response(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");

            MusicBand newBand = add.inputMusicBand();
            return collectionManager.addMusicBandIfMax(newBand);
        } catch (InputBreakException e) {
            return new Response(true, e.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}