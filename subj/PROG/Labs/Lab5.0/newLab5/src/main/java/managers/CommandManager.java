package managers;

import commands.Command;

import java.util.HashMap;

public class CommandManager {
    private HashMap<String, Command> commandsMap = new HashMap<>();


    public void add(String name, Command command) {
        commandsMap.put(name, command);
    }

    public HashMap<String, Command> getCommandsMap() {
        return commandsMap;
    }
}
