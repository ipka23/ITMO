package common_utility.network;

import common_entities.MusicBand;
import common_utility.database.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Request implements Serializable {
    @Serial
    private static final long serialVersionUID = -3953205064390766513L;
    private String message;
    private String fileName;
    private File scriptFile;
    private User user;
    private MusicBand musicBand;
    private MusicBand newBand;
    private MusicBand oldBand;
    @Getter
    private boolean flag = true;

    public Request(String message) {
        this.message = message;
    }

    public Request(String message, User user) {
        this.message = message;
        this.user = user;
    }


    public Request(String message, User currentUser, MusicBand band) {
        this.message = message;
        this.user = currentUser;
        this.musicBand = band;
    }

    public Request(String message, User currentUser, MusicBand oldBand, MusicBand newBand) {
        this.message = message;
        this.user = currentUser;
        this.oldBand = oldBand;
        this.newBand = newBand;
    }

    @Override
    public String toString() {
        return "\nRequest [\nmessage=" + message + ",\nfileName=" + fileName + ",\nscriptFile=" + scriptFile + ",\nuser=" + user + ",\nmusicBand=" + musicBand + "\n]";
    }
}
