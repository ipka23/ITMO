package client_utility;

import java.io.Serializable;

public class Request implements Serializable {
    String commandName;
    String commandArgs;
    public Request(String commandName, String commandArgs) {
        this.commandName = commandName;
        this.commandArgs = commandArgs;
    }
}
