package server_utility;

import common_utility.network.Response;
import server_utility.interfaces.Executable;

import java.io.IOException;


public abstract class Command implements Executable {
    private String name;
    private String description;
//    private boolean hasArguments;  boolean hasArguments

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public abstract Response execute(String[] command) throws IOException, ClassNotFoundException;
}
