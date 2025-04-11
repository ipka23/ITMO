package server_commands;

import common_entities.MusicBand;
import common_utility.network.Response;
import server_managers.CollectionManager;
import server_utility.Command;
import server_utility.consoles.ClientConsole;
import server_utility.exceptions.InputBreakException;

import java.io.IOException;

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
    public AddIfMin(ClientConsole console, CollectionManager collectionManager) {
        super("add_if_min", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
        this.add = new Add(console, collectionManager);
    }


    @Override
    public Response execute(String[] command) {
        try {
            if (!command[1].isEmpty())
                return new Response(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
            long id = collectionManager.getFreeId();
            MusicBand newBand = add.inputMusicBand();
            if (newBand.getBestAlbum().getSales() < collectionManager.getMin().getBestAlbum().getSales()) {
                collectionManager.addMusicBand(newBand);
                return new Response(false, "В коллекцию была добавлена музыкальная группа, количество продаж лучшего альбома которой меньше чем у группы с минимальным количеством продаж!");
            } else {
                collectionManager.removeByID(id);
                return new Response(false, "Музыкальная группа не была добавлена в коллекцию, т. к. количество продаж её лучшего альбома больше чем у группы с минимальным количеством продаж!");
            }
        } catch (InputBreakException e) {
            return new Response(true, e.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}