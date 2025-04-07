package common_utility.network;

import common_entities.MusicBand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Request implements Serializable {
    private String[] userCommand;
    private String message;
    private MusicBand musicBand;
    private String command;
    private String arg;
    private String fileName;
    private File scriptFile;
    private Collection<MusicBand> musicBandsCollection;

    public Request(String message) {
        this.message = message;
    }

    public Request(String command, String arg) {
        this.command = command;
        this.arg = arg;
        message = arg + command;
    }


}
