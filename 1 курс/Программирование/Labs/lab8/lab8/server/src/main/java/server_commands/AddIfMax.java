package server_commands;

import common_entities.MusicBand;
import common_utility.localization.LanguageManager;
import common_utility.network.Request;
import common_utility.network.Response;
import server_managers.CollectionManager;
import server_utility.Command;
import server_utility.RCommand;
import server_utility.consoles.ClientConsole;
import server_utility.exceptions.InputBreakException;
import server_utility.multithreading.Refresher;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;

/**
 * Данный класс отвечает за выполнение команды "add_if_max"
 *
 * @author ipka23
 */
public class AddIfMax extends RCommand {
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
        super(LanguageManager.getBundle().getString("add_if_max"), LanguageManager.getBundle().getString("add_if_maxDescription"));
        this.console = console;
        this.collectionManager = collectionManager;
        this.add = new Add(console, collectionManager, inFromClient, outToClient);
    }

    @Override
    public Response execute(String[] command) throws IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public Response execute(String[] command, Request request) {
        try {
            MusicBand newBand = add.getBandFromRequest(request);
            if(!newBand.validate()) {
                return new Response(false, collectionManager.getString("validationError"));
            }
            newBand.setCreationDate(LocalDate.now());

            Response response = collectionManager.addMusicBandIfMax(newBand);
            response.setMusicBand(newBand);
            Refresher.addRefresh(collectionManager.getCollection());
            return response;
        } catch (InputBreakException | IOException | ClassNotFoundException e) {
            return new Response(false, collectionManager.getString("error"));
        }
    }
}