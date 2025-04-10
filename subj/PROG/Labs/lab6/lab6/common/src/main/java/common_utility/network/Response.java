package common_utility.network;

import common_entities.MusicBand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
        this.message = null;
    }

    public Response(boolean exitClient, String message, Collection<MusicBand> musicBandsCollection) {
        this.exitClient = exitClient;
        this.message = message;
        this.musicBandsCollection = musicBandsCollection;
    }

    public boolean getExitStatus() {
        return exitClient;
    }


//    @Override
//    public String toString() {
//        return message;
//    }
}
