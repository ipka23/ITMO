package server_commands;

import common_utility.localization.LanguageManager;
import lombok.extern.slf4j.Slf4j;
import common_entities.MusicBand;
import common_utility.network.Request;
import common_utility.network.Response;
import lombok.Setter;
import server_managers.CollectionManager;
import server_utility.multithreading.Refresher;
import server_utility.RCommand;
import server_utility.consoles.ClientConsole;
import server_utility.exceptions.InputBreakException;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.Collection;


@Slf4j
public class Add extends RCommand {
    @Setter
    private ObjectInputStream inFromClient;
    @Setter
    private ObjectOutputStream outToClient;
    private ClientConsole console;
    private CollectionManager collectionManager;
    private String ADD_PROMPT = "* ";
    @Setter
    private File scriptFile;

    public Add(ClientConsole console, CollectionManager collectionManager, ObjectInputStream inFromClient, ObjectOutputStream outToClient) {
        super(LanguageManager.getBundle().getString("add"), LanguageManager.getBundle().getString("addDescription"));
        this.console = console;
        this.collectionManager = collectionManager;
        this.inFromClient = inFromClient;
        this.outToClient = outToClient;
        this.scriptFile = console.getScriptFile();
    }

    public MusicBand getBandFromRequest(Request request) throws IOException, ClassNotFoundException {
        return request.getMusicBand();
    }

    @Override
    public Response execute(String[] command) throws IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public Response execute(String[] command, Request request) {
        Response response;
        try {
            MusicBand musicBand = getBandFromRequest(request);
            musicBand.setCreationDate(LocalDate.now());
            if(!musicBand.validate()) {
                return new Response(false, collectionManager.getString("validationError"));
            }

//            System.out.println("size before add: " + collectionManager.getCollection().size());
            response = collectionManager.addMusicBand(musicBand);
//            System.out.println("size after add: " + collectionManager.getCollection().size());
            Collection<MusicBand> collection = collectionManager.getCollection();
            response.setMusicBandsCollection(collection);
            response.setMusicBand(response.getMusicBand());
            Refresher.refresh(collection);
            return response;
        } catch (InputBreakException | IOException | ClassNotFoundException e) {
            return new Response(false, collectionManager.getString("error"));
        }

    }
}
