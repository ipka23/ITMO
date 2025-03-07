package utility;

import utility.interfaces.Executable;

public abstract class Command implements Executable {
    private final String NAME;
    private final String DESCRIPTION;

    public Command(String name, String description) {
        this.NAME = name;
        this.DESCRIPTION = description;
    }

    public abstract ExecutionResponse execute(String[] userCommand);

    public String getName() {
        return NAME;
    }

    public String getDescription() {
        return DESCRIPTION;
    }
}
