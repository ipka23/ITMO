
package network;

import common_entities.MusicBand;
import common_utility.network.Response;
import fx.controllers.VisualizationController;
import javafx.application.Platform;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

public class ResponseHandler extends Thread {
    private final ObjectInputStream in;
    private final ObservableList<MusicBand> observableList;
    private VisualizationController visualizationController;
    private final BlockingQueue<Response> responses = new LinkedBlockingQueue<>();
    public ResponseHandler(ObjectInputStream in, ObservableList<MusicBand> observableList, VisualizationController visualizationController) {
        this.in = in;
        this.observableList = observableList;
        this.visualizationController = visualizationController;
        setDaemon(true);
    }

    public Response getResponse() throws InterruptedException {
        return responses.take();
    }
    @Override
    public void run() {
        try {
            while (true) {
                Response response = (Response) in.readObject();
//                System.out.println("Response message: " + response.getMessage());
//                System.out.println("Response collection: " + response.getMusicBandsCollection());
                Collection<MusicBand> collection = response.getMusicBandsCollection();
                if (response.getMessage().equals("add_refresh")) {
                    Platform.runLater(() -> {
                        for (MusicBand band :  new HashSet<>(observableList)) {
                            if (!collection.contains(band)) {
                                visualizationController.eraseMusicBand(
                                        band,
                                        visualizationController.getColor(band)
                                );
                            }
                        }
                        for (MusicBand band : collection) {
                            visualizationController.drawMusicBand(band);
                        }
                        observableList.setAll(collection);
                    });
                }
                if (response.getMessage().equals("update_refresh")) {
                    MusicBand oldBand = response.getOldBand();
                    MusicBand newBand = response.getNewBand();
                    Platform.runLater(() -> {
                        visualizationController.eraseMusicBand(oldBand, visualizationController.getColor(oldBand));  // TODO FIX
                        visualizationController.drawMusicBand(newBand);
//                        observableList.remove(oldBand);
//                        observableList.add(newBand);
                        observableList.setAll(collection);
                    });
                }
                if (response.getMessage().equals("delete_refresh")) {
//                    System.out.println("observableList size: " + observableList.size());
//                    System.out.println("collection size: " + collection.size());
                    Platform.runLater(() -> {
                        for (MusicBand band : new HashSet<>(observableList)) {
                            if (!collection.contains(band)) {
                                visualizationController.eraseMusicBand(band, visualizationController.getColor(band));
                            }
                        }
                        observableList.setAll(collection);
                    });
                }
                else {
                    responses.put(response);
                }
            }
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            throw new RuntimeException(e);

        }
    }
}