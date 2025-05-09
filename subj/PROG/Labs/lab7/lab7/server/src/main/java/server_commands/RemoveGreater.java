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
import java.util.Collection;
import java.util.Iterator;

/**
 * Данный класс отвечает за выполнение команды "remove_greater"
 *
 * @author ipka23
 */
public class RemoveGreater extends Command {
    private final ClientConsole console;
    private final CollectionManager collectionManager;
    private final Add add;

    /**
     * Конструктор
     *
     * @param console           интерфейс Console для взаимодействия с консолью
     * @param collectionManager объект CollectionManager для управления коллекцией
     */
    public RemoveGreater(ClientConsole console, CollectionManager collectionManager, ObjectInputStream inFromClient, ObjectOutputStream outToClient) {
        super("remove_greater", "удалить из коллекции все элементы, превышающие заданный");
        this.console = console;
        this.collectionManager = collectionManager;
        this.add = new Add(console, collectionManager, inFromClient, outToClient);
    }


    public Response execute(String[] command) {
        if (!command[1].trim().isEmpty())
            return new Response(true, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\""); //todo fix
        Collection<MusicBand> collection = collectionManager.getCollection();

        try {
            MusicBand newBand = add.inputMusicBand();
            Response response = collectionManager.addMusicBand(newBand);
            response.setExitStatus(true);
            Iterator<MusicBand> iterator = collection.iterator();
            if (collection.isEmpty()) return new Response(false, "Коллекция пуста!");
            String owner = newBand.getOwner();
            while (iterator.hasNext()) {
                String current_user;
                if (iterator.next().getBestAlbum().getSales() > newBand.getBestAlbum().getSales()) {
                    current_user = iterator.next().getOwner();
                    if (current_user.equals(owner)) {
                        iterator.remove();
                    } else iterator.next();
                }
            }
            //todo

            collectionManager.setCollection(collection);
            return new Response(true, "Из коллекции были удалены все музыкальные группы превышающие данный по количеству продаж лучшего альбома", collectionManager.getCollection());
        } catch (InputBreakException e) {
            return new Response(false, e.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}