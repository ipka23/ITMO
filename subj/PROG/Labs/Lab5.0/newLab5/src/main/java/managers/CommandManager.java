package managers;

import commands.*;
import utility.Command;
import utility.interfaces.Console;
import utility.Invoker;

import java.util.HashMap;
import java.util.LinkedList;

public class CommandManager {
    private final Console CONSOLE;
    private final CollectionManager COLLECTION_MANAGER;
    private Invoker invoker;
    private LinkedList<String> history = new LinkedList<>();
    private int historyIndex = -1;
    private HashMap<String, Command> commandsMap = new HashMap<>();

    public CommandManager(Console console, CollectionManager collectionManager) {
        this.CONSOLE = console;
        this.COLLECTION_MANAGER = collectionManager;
        commandsMap.put("add", new Add(console, collectionManager));
        commandsMap.put("help", new Help(console, this));
        commandsMap.put("info", new Info(console, collectionManager));
        commandsMap.put("clear", new Clear(console, collectionManager));
        commandsMap.put("save", new Save(console, collectionManager));
        commandsMap.put("history", new History(console, this));
        commandsMap.put("show", new Show(console, collectionManager));
        commandsMap.put("remove_by_id", new RemoveByID(console, collectionManager));
        commandsMap.put("update", new Update(console, collectionManager));
        commandsMap.put("add_if_max", new AddIfMax(console, collectionManager));
        commandsMap.put("add_if_min", new AddIfMin(console, collectionManager));
        commandsMap.put("remove_greater", new RemoveGreater(console, collectionManager));
        commandsMap.put("max_by_best_album", new MaxByBestBestAlbum(console, collectionManager));
        commandsMap.put("filter_starts_with_name", new FilterStartsWithName(console, collectionManager));
        commandsMap.put("print_field_ascending_establishment_date", new PrintFieldAscendingEstablishmentDate(console, collectionManager));
        commandsMap.put("execute_script", new ExecuteScript(console, collectionManager, this, invoker));
        commandsMap.put("exit", new Exit());
    }

    public void setRunner(Invoker invoker) {
        this.invoker = invoker;
        ((ExecuteScript) commandsMap.get("execute_script")).setRunner(invoker);
    }

    public void addToHistory(String userCommand) {
        history.add(userCommand.trim());
        historyIndex = history.size();
    }

    public LinkedList<String> getHistory() {
        return history;
    }

    public int getHistorySize() {
        return history.size();
    }

    public HashMap<String, Command> getCommandsMap() {
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

    public int getHistoryIndex() {
        return historyIndex;
    }

    public void resetHistoryIndex() {
        historyIndex = history.size();
    }
}