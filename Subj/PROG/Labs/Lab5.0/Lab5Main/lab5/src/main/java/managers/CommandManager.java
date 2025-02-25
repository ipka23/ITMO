package managers;

import commands.Command;

import java.util.*;

public class CommandManager {
    private final Map<String, Command> commands = new LinkedHashMap<>();


    public void addCommand(String name, Command command) {
        commands.put(name, command);
    }



    public Collection<Command> getCommands() {
        return commands.values();
    }

    public Map<String, Command> getCommandsMap() {
        return commands;
    }


}
