package common_utility.network;

import common_entities.MusicBand;

import java.io.Serializable;
import java.util.Collection;

public class Response implements Serializable {
    private boolean exitClient;
    private String message;
    private MusicBand band;
    private Collection<MusicBand> musicBandsCollection;

    public Response(boolean exitClient, String message) {
        this.exitClient = exitClient;
        this.message = message;
    }
    public Response(boolean exitClient, String message, MusicBand band) {
        this.exitClient = exitClient;
        this.message = message;
        this.band = band;
    }

    public Response(boolean exitClient) {
        this.exitClient = exitClient;
        this.message = "";
    }

    public Response(boolean exitClient, String message, Collection<MusicBand> musicBandsCollection) {
        this.exitClient = exitClient;
        this.message = message;
        this.musicBandsCollection = musicBandsCollection;
    }

    public boolean getExitStatus() {
        return exitClient;
    }


    public String getMessage() {
        return message;
    }

    public Collection<MusicBand> getMusicBandsCollection() {
        return musicBandsCollection;
    }

    public MusicBand getBand() {
        return band;
    }

    @Override
    public String toString() {
        return message;
    }
}
