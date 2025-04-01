package common_utility.network;

import common_entities.MusicBand;

import java.io.File;
import java.io.Serializable;
import java.util.Collection;

public class Request implements Serializable {
    private String message;
    private MusicBand musicBand;
    private Collection<MusicBand> musicBandsCollection;
    private File scriptFile;

    public Request(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMusicBand(MusicBand musicBand) {
        this.musicBand = musicBand;
    }

    public void setMusicBandsCollection(Collection<MusicBand> musicBandsCollection) {
        this.musicBandsCollection = musicBandsCollection;
    }

    public void setScriptFile(File scriptFile) {
        this.scriptFile = scriptFile;
    }

    public Request(String message, Collection<MusicBand> musicBandsCollection, File scriptFile) {
        this.message = message;
        this.musicBandsCollection = musicBandsCollection;
        this.scriptFile = scriptFile;
    }

    public File getScriptFile() {
        return scriptFile;
    }

    public Request(String message, Collection<MusicBand> musicBandsCollection) {
        this.message = message;
        this.musicBandsCollection = musicBandsCollection;
    }

    public Request(String message, MusicBand musicBand) {
        this.message = message;
        this.musicBand = musicBand;
    }

    public String getMessage() {
        return message;
    }

    public MusicBand getMusicBand() {
        return musicBand;
    }

    public Collection<MusicBand> getMusicBandsCollection() {
        return musicBandsCollection;
    }
}
