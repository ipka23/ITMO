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
    private boolean exitStatus;
    private String message;
    private MusicBand band;
    private Collection<MusicBand> musicBandsCollection;

    public Response(boolean exitStatus, String message) {
        this.exitStatus = exitStatus;
        this.message = message;
    }
    public Response(boolean exitClient, String message, MusicBand band) {
        this.exitStatus = exitClient;
        this.message = message;
        this.band = band;
    }

    public Response(boolean exitStatus) {
        this.exitStatus = exitStatus;
        this.message = null;
    }

    public Response(boolean exitClient, String message, Collection<MusicBand> musicBandsCollection) {
        this.exitStatus = exitClient;
        this.message = message;
        this.musicBandsCollection = musicBandsCollection;
    }

    public boolean getExitStatus() {
        return exitStatus;
    }


//    @Override
//    public String toString() {
//        return message;
//    }
}
