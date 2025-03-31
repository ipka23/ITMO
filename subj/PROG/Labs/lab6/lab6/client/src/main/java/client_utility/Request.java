package client_utility;
//import entities.MusicBand;

import java.io.Serializable;

public class Request implements Serializable {
    String commandName;
    String commandArgs;
//    MusicBand band;
    public Request(String commandName, String commandArgs) {
        this.commandName = commandName;
        this.commandArgs = commandArgs;
//        this.band = band;
    }
}
