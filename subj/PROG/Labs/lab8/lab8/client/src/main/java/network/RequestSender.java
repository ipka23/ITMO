package network;

import common_entities.MusicBand;
import common_utility.database.User;
import common_utility.localization.LanguageManager;
import common_utility.network.Request;
import common_utility.network.Response;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashSet;

@Getter
@Setter
public class RequestSender {
    private static Collection<MusicBand> musicBandsCollection = new HashSet<>();

    private ObjectOutputStream outToServer;
    private ObjectInputStream inFromServer;

    public RequestSender(ObjectOutputStream outToServer, ObjectInputStream inFromServer) throws IOException {
        this.outToServer = outToServer;
        this.inFromServer = inFromServer;
    }


    public void sendRequest(Request r, ObjectOutputStream outToServer) throws IOException {
        outToServer.writeObject(r);
        outToServer.flush();
    }

    public Response getResponse(ObjectInputStream inFromServer) throws IOException, ClassNotFoundException {
        return (Response) inFromServer.readObject();
    }

}
