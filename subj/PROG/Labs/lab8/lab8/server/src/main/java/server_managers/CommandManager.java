package server_managers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import server_commands.*;
import server_utility.Command;
import server_utility.Invoker;
import server_utility.consoles.ClientConsole;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

//Console, CollectionManager
@Getter
@Setter
@AllArgsConstructor
public class CommandManager {
    public static final Map<String, Command> commandsMap = new HashMap<>();

    public Command getCommand(String commandName) {
        return commandsMap.get(commandName);
    }

    public void addCommand(String commandName, Command command) {
        commandsMap.put(commandName, command);
    }

    public void declareCommands(ClientConsole console, CollectionManager collectionManager, Invoker invoker, ObjectInputStream inFromClient, ObjectOutputStream outToClient) {
        commandsMap.put("add", new Add(console, collectionManager, inFromClient, outToClient));
        commandsMap.put("add_if_max", new AddIfMax(console, collectionManager, inFromClient, outToClient));
        commandsMap.put("add_if_min", new AddIfMin(console, collectionManager, inFromClient, outToClient));
//        commandsMap.put("execute", new ExecuteScript(console, invoker, collectionManager, inFromClient, outToClient));
        commandsMap.put("update", new Update(console, collectionManager, inFromClient, outToClient));
        commandsMap.put("remove_greater", new RemoveGreater(console, collectionManager, inFromClient, outToClient));
        commandsMap.put("clear", new Clear(console, collectionManager));
//        commandsMap.put("exit", new Exit(collectionManager, outToClient));
//todo в фильтрации таблицы        commandsMap.put("filter_starts_with_name", new FilterStartsWithName(console, collectionManager));
        commandsMap.put("help", new Help(console, this));
        commandsMap.put("info", new Info(console, collectionManager));
//todo в фильтрации таблицы        commandsMap.put("max_by_best_album", new MaxByBestBestAlbum(console, collectionManager));
//todo в фильтрации таблицы        commandsMap.put("print_field_ascending_establishment_date", new PrintFieldAscendingEstablishmentDate(console, collectionManager));
        commandsMap.put("remove", new Remove(console, collectionManager));
//        commandsMap.put("show", new Show(console, collectionManager));
//        commandsMap.put("show_scripts", new ShowScripts());
    }
}
