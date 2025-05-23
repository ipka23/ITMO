
package fx;

import common_entities.MusicBand;
import common_utility.network.Response;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ResponseHandler extends Thread {
    private final ObjectInputStream in;
    private final ObservableList<MusicBand> observableList;
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
                System.out.println("Response message: " + response.getMessage());
                System.out.println("Response collection: " + response.getMusicBandsCollection());
                System.out.println("Response band: " + response.getMusicBand());
                Collection<MusicBand> collection = response.getMusicBandsCollection();
                if (response.getMessage().equals("refresh")) {
                    Platform.runLater(() -> observableList.setAll(collection));
                } else if (response.getMessage().equals("remove_refresh")) {
                    Platform.runLater(() -> observableList.remove(response.getMusicBand()));
                }
                else {
//                    System.out.println("bands: " + response.getMusicBandsCollection());
                    responses.put(response);
                }
            }
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            throw new RuntimeException(e);

        }
    }
}
