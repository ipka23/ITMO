package server_managers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import server_commands.*;
import server_utility.Command;
import server_utility.consoles.ClientConsole;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
//Console, CollectionManager
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommandManager {
    private Map<String, Command> commandsMap = new HashMap<>();
    private ClientConsole console;
    private CollectionManager collectionManager;
    private LinkedList<String> history = new LinkedList<>();
    private int historyIndex = -1;

    public CommandManager(ClientConsole console, CollectionManager collectionManager) {
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

    public Command getCommand(String commandName) {
        return commandsMap.get(commandName);
    }

    public void addCommand(String commandName, Command command) {
        commandsMap.put(commandName, command);
    }
}
