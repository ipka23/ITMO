package server_commands;

import common_entities.MusicBand;
import common_utility.network.Response;
import server_managers.CollectionManager;
import server_utility.Command;
import server_utility.consoles.ClientConsole;
import server_utility.exceptions.InputBreakException;

import java.io.IOException;
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
    public RemoveGreater(ClientConsole console, CollectionManager collectionManager) {
        super("remove_greater", "удалить из коллекции все элементы, превышающие заданный");
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
    public Response execute(String[] command) {
        if (!command[1].trim().isEmpty())
            return new Response(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
        Collection<MusicBand> collection = collectionManager.getCollection();

        try {
            MusicBand newBand = add.inputMusicBand();
            Iterator<MusicBand> iterator = collection.iterator();
            if (collection.isEmpty()) return new Response(false, "Коллекция пуста!");
            while (iterator.hasNext()) {
                if (iterator.next().getBestAlbum().getSales() > newBand.getBestAlbum().getSales()) {
                    iterator.remove();
                }
            }
            return new Response(false, "Из коллекции были удалены все элементы превышающие данный по параметру album.sales");
        } catch (InputBreakException e) {
            return new Response(true, e.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}