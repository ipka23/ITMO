package server_commands;
import common_utility.localization.LanguageManager;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import common_entities.Album;
import common_entities.Coordinates;
import common_entities.MusicBand;
import common_entities.MusicGenre;
import common_utility.network.Request;
import common_utility.network.Response;
import lombok.Setter;
import server_managers.CollectionManager;
import server_utility.Command;
import server_utility.consoles.ClientConsole;
import server_utility.exceptions.InputBreakException;
import server_utility.exceptions.InputException;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;


@Slf4j
public class Add extends Command {
    @Setter
    private ObjectInputStream inFromClient;
    @Setter
    private ObjectOutputStream outToClient;
    private ClientConsole console;
    private CollectionManager collectionManager;
    private String ADD_PROMPT = "* ";
    @Setter
    private File scriptFile;

    public Add(ClientConsole console, CollectionManager collectionManager, ObjectInputStream inFromClient, ObjectOutputStream outToClient)  {
        super("add", "добавить новый элемент в коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
        this.inFromClient = inFromClient;
        this.outToClient = outToClient;
        this.scriptFile = console.getScriptFile();
    }
    public MusicBand inputMusicBand(Request request) throws IOException, ClassNotFoundException {
        return request.getMusicBand();
    }
    public MusicBand inputMusicBand() throws IOException, ClassNotFoundException {
        return null; // todo
    }
    @Override
    public Response execute(String[] command) throws IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public Response execute(String[] command, Request request) {
        Response response = null;
        try {
            MusicBand musicBand = inputMusicBand(request);
            response =  collectionManager.addMusicBand(musicBand);
            response.setExitStatus(true); // конец ввода банды
        } catch (InputBreakException | IOException | ClassNotFoundException e) {}
        if (response != null) return response;
        return new Response(true, collectionManager.getString("error"));
    }
}
