package server_utility;

import common_utility.ExecutionResponse;
import server_utility.interfaces.Executable;


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

    public abstract ExecutionResponse execute(String[] command);
}
