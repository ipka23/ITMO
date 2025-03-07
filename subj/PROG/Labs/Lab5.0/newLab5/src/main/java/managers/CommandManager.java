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
        commandsMap.put("exit", new Exit(console));
    }

    public void setRunner(Invoker invoker) {
        this.invoker = invoker;
        ((ExecuteScript) commandsMap.get("execute_script")).setRunner(invoker);
    }

    public void addToHistory(String[] userCommand) {
        StringBuilder s = new StringBuilder();
        history.add(s.append(userCommand[0]).append(" ").append(userCommand[1]).toString().trim());
    }

    public LinkedList<String> getHistory() {
        return history;
    }

    public HashMap<String, Command> getCommandsMap() {
        return commandsMap;
    }

    public Command getCommand(String command) {
        return commandsMap.get(command);
    }

    public String getCommands() {
        StringBuilder stringBuilder = new StringBuilder();
        int cnt = 0;
        for (Command command : commandsMap.values()) {
            if (cnt > 0 && cnt % 4 == 0) {
                stringBuilder.append("\n");
            }
            stringBuilder.append(command.getName()).append(" ");
            cnt++;

        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    public LinkedList<String> getCommandsList() {
        LinkedList<String> commands = new LinkedList<>();
        for (Command command : commandsMap.values()) {
            commands.add(command.getName());
        }
        return commands;
    }
}
