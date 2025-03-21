package managers;

import commands.*;
import utility.Command;
import utility.Invoker;
import utility.interfaces.Console;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
//Console, CollectionManager
public class CommandManager {
    private Map<String, Command> commandsMap = new HashMap<>();
    private Console console;
    private CollectionManager collectionManager;
    private LinkedList<String> history = new LinkedList<>();
    private int historyIndex = -1;

    public CommandManager(Console console, CollectionManager collectionManager) {
        this.console = console;
        this.collectionManager = collectionManager;
        commandsMap.put("add", new Add(console, collectionManager));
        commandsMap.put("add_if_max", new AddIfMax(console, collectionManager));
        commandsMap.put("add_if_min", new AddIfMin(console, collectionManager));
        commandsMap.put("clear", new Clear(console, collectionManager));
        commandsMap.put("exit", new Exit());
        commandsMap.put("filter_starts_with_name", new FilterStartsWithName(console, collectionManager));
        commandsMap.put("help", new Help(console, this));
        commandsMap.put("info", new Info(console, collectionManager));
        commandsMap.put("max_by_best_album", new MaxByBestBestAlbum(console, collectionManager));
        commandsMap.put("print_field_ascending_establishment_date", new PrintFieldAscendingEstablishmentDate(console, collectionManager));
        commandsMap.put("remove_by_id", new RemoveByID(console, collectionManager));
        commandsMap.put("remove_greater", new RemoveGreater(console, collectionManager));
        commandsMap.put("show", new Show(console, collectionManager));
        commandsMap.put("update", new Update(console, collectionManager));
    }


    public void setCommandsMap(Map<String, Command> commandsMap) {
        this.commandsMap = commandsMap;
    }

    public void setConsole(Console console) {
        this.console = console;
    }


    public void addCommand(String name, Command command) {
        commandsMap.put(name, command);
    }

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public Command getCommand(String commandName) {
        return commandsMap.get(commandName);
    }

    public Map<String, Command> getCommandsMap() {
        return commandsMap;
    }

    public String getPreviousCommand() {
        if (historyIndex > 0) {
            historyIndex--;
            return history.get(historyIndex);
        }
        return null;
    }

    public String getNextCommand() {
        if (historyIndex < history.size() - 1) {
            historyIndex++;
            return history.get(historyIndex);
        } else if (historyIndex == history.size() - 1) {
            historyIndex++;
        }
        return null;
    }

    public void addToHistory(String userCommand) {
        history.add(userCommand.trim());
        historyIndex = history.size();
    }

    public void resetHistoryIndex() {
        historyIndex = history.size();
    }

}
