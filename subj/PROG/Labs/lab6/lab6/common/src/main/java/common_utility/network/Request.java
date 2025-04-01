package common_utility.network;

import common_entities.MusicBand;

import java.io.Serializable;

public class Request implements Serializable {
    private String userCommand;
    private MusicBand musicBand;
    public Request(String userCommand, MusicBand musicBand) {
        this.userCommand = userCommand;
        this.musicBand = musicBand;
    }
}
