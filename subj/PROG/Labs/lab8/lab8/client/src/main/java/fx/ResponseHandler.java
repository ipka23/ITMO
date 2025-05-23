
package fx;

import common_entities.MusicBand;
import common_utility.network.Response;
import javafx.application.Platform;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ResponseHandler extends Thread {
    private final ObjectInputStream in;
    private ObservableList<MusicBand> observableList;
    private final BlockingQueue<Response> responses = new LinkedBlockingQueue<>();
    public ResponseHandler(ObjectInputStream in, ObservableList<MusicBand> observableList) {
        this.in = in;
        this.observableList = observableList;
        setDaemon(true);
    }

    public Response getResponse() throws InterruptedException, IOException, ClassNotFoundException {
        return responses.take();
    }
    @Override
    public void run() {

        try {
            while (true) {
                Response response = (Response) in.readObject();
                System.out.println("Response: " + response.getMessage());
                if (response.getMessage().equals("refresh")) {
                    System.out.println("refresh bands: " + response.getMusicBandsCollection());
                    System.out.println("Handler.observableList: " + System.identityHashCode(observableList));
                    Platform.runLater(() -> observableList.setAll(response.getMusicBandsCollection()));
                } else {
                    responses.put(response);
                }
            }
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            throw new RuntimeException(e);

        }
    }
}
