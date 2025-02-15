package Commands;

import utility.ExecutionResponse;

public abstract class Command {
    public final String name;
    public final String description;
    public Command(String command, String description) {
        this.name = command;
        this.description = description;
    }

    public abstract ExecutionResponse execute(String[] args);

    public String getName() {
        return name;
    }
}
