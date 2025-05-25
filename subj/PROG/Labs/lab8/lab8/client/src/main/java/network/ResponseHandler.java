
package network;

import common_entities.MusicBand;
import common_utility.network.Response;
import fx.controllers.VisualizationController;
import javafx.application.Platform;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

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
                Collection<MusicBand> collection = response.getMusicBandsCollection();
                if (response.getMessage().equals("refresh")) {
                    Platform.runLater(() -> {
                        for (MusicBand band : collection) {
                            VisualizationController.drawMusicBand(band);
                        }
                        observableList.setAll(collection);
                    });
                }
                if (response.getMessage().equals("delete_refresh")) {
                    System.out.println("observableList size: " + observableList.size());
                    System.out.println("collection size: " + collection.size());
                    Platform.runLater(() -> {
                        for (MusicBand band : observableList) {
                            if (!collection.contains(band)) {
                                VisualizationController.eraseMusicBand(band.getCoordinates().getX(), band.getCoordinates().getY(), VisualizationController.getColor(band));
                            }
                        }
                        observableList.setAll(collection);
                    });
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
