package common_utility.network;

import common_entities.MusicBand;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
public class Response implements Serializable {
    @Serial
    private static final long serialVersionUID = -2026450900467574978L;
    private boolean exitStatus;
    private String message;
    private Collection<MusicBand> musicBandsCollection;
    private MusicBand musicBand;


    public Response(boolean exitStatus, String message) {
        this.exitStatus = exitStatus;
        this.message = message;
    }

    public Response(boolean exitStatus) {
        this.exitStatus = exitStatus;
        this.message = null;
    }

    public Response(boolean exitStatus, String message, Collection<MusicBand> musicBandsCollection) {
        this.exitStatus = exitStatus;
        this.message = message;
        this.musicBandsCollection = musicBandsCollection;
    }
    public Response(boolean exitStatus, String message, Collection<MusicBand> musicBandsCollection, MusicBand musicBand) {
        this.exitStatus = exitStatus;
        this.message = message;
        this.musicBandsCollection = musicBandsCollection;
        this.musicBand = musicBand;
    }

    public boolean getExitStatus() {
        return exitStatus;
    }

    @Override
    public String toString() {
        return "Response [\nexitStatus=" + exitStatus + ",\nmessage=" + message + ",\nmusicBandsCollection=" + musicBandsCollection + "\n]";
    }
}