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
 * Данный класс отвечает за выполнение команды "update"
 *
 * @author ipka23
 */
public class Update extends Command {
    private final ClientConsole console;
    private final CollectionManager collectionManager;
    private final Add add;
    private ObjectInputStream inFromClient;
    private ObjectOutputStream outToClient;

    /**
     * Конструктор
     *
     * @param console           интерфейс Console для взаимодействия с консолью
     * @param collectionManager объект CollectionManager для управления коллекцией
     */
    public Update(ClientConsole console, CollectionManager collectionManager, ObjectInputStream inFromClient, ObjectOutputStream outToClient) {
        super("update id", "обновить значение элемента коллекции, id которого равен заданному");
        this.console = console;
        this.collectionManager = collectionManager;
        this.add = new Add(console, collectionManager, inFromClient, outToClient);
        this.inFromClient = inFromClient;
        this.outToClient = outToClient;
    }

    public Response execute(String[] command) {
        if (command[1].trim().isEmpty())
            return new Response(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
        long id;
        try {
            id = Long.parseLong(command[1].trim());
        } catch (NumberFormatException e) {
            return new Response(false, "Неверный формат id!");
        }
        MusicBand band = collectionManager.getMusicBandById(id);
        if (band == null || !collectionManager.getCollection().contains(band)) {
            return new Response(false, "В коллекции нет музыкальной группы с таким id!");
        }
        MusicBand newBand;
        try {
            console.println("--------------------------------Введите новые данные музыкальной группы--------------------------------");
            newBand = add.inputMusicBand();
        } catch (InputBreakException e) {
            return new Response(true, "Отмена ввода...");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        band.update(newBand);
        return new Response(false, "Элемент с id = " + id + " был обновлён!");
    }
}