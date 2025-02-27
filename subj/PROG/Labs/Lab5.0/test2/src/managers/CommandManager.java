package managers;

import commands.Command;

import java.util.*;

public class CommandManager {
    private final Map<String, Command> commands = new LinkedHashMap<>();
    private final List<String> commandHistory = new ArrayList<>();


    public void addCommand(Command command) {
        commands.put(command.getName(), command);
    }

    public void addCommandToHistory(String command) {
        commandHistory.add(command);
    }

    public Collection<Command> getCommands() {
        return commands.values();
    }

    public Map<String, Command> getCommandsMap() {
        return commands;
    }

    public List<String> getCommandHistory() {
        return commandHistory;
    }

}
