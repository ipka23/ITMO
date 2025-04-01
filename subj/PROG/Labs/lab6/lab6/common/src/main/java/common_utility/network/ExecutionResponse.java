package common_utility.network;

import common_entities.MusicBand;

import java.io.Serializable;

public class ExecutionResponse implements Serializable {
    private boolean exitClient;
    private String message;
    private MusicBand band;

    public ExecutionResponse(boolean exitClient, String message) {
        this.exitClient = exitClient;
        this.message = message;
    }
    public ExecutionResponse(boolean exitClient, String message, MusicBand band) {
        this.exitClient = exitClient;
        this.message = message;
        this.band = band;
    }
    public ExecutionResponse(boolean exitClient) {
        this.exitClient = exitClient;
        this.message = "";
    }
    public boolean getExitStatus() {
        return exitClient;
    }


    public String getMessage() {
        return message;
    }
    @Override
    public String toString() {
        return message;
    }
}
