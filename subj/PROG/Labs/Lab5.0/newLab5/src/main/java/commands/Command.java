package commands;

import utility.ExecutionResponse;

public abstract class Command {
    private final String NAME;
    private final String DESCRIPTION;

    public Command(String name, String description) {
        this.NAME = name;
        this.DESCRIPTION = description;
    }

    public abstract ExecutionResponse execute(String[] args);

    public String getName() {
        return NAME;
    }

    public String getDescription() {
        return DESCRIPTION;
    }
}
