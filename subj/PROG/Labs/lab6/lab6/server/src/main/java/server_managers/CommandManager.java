package server_managers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
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
@NoArgsConstructor
public class CommandManager {
    private Map<String, Command> commandsMap = new HashMap<>();


    public Command getCommand(String commandName) {
        return commandsMap.get(commandName);
    }

    public void addCommand(String commandName, Command command) {
        commandsMap.put(commandName, command);
    }

    public void declareCommands(ClientConsole console, CollectionManager collectionManager, Invoker invoker, ObjectInputStream inFromClient, ObjectOutputStream outToClient, Logger log)   {
        commandsMap.put("add", new Add(console, collectionManager, inFromClient, outToClient));
        commandsMap.put("add_if_max", new AddIfMax(console, collectionManager));
        commandsMap.put("add_if_min", new AddIfMin(console, collectionManager));
        commandsMap.put("execute_script", new ExecuteScript(console, invoker, collectionManager, inFromClient, outToClient));
        commandsMap.put("update", new Update(console, collectionManager, inFromClient, outToClient));
        commandsMap.put("remove_greater", new RemoveGreater(console, collectionManager, inFromClient, outToClient));
        commandsMap.put("clear", new Clear(console, collectionManager));
        commandsMap.put("exit", new Exit(collectionManager, log));
        commandsMap.put("filter_starts_with_name", new FilterStartsWithName(console, collectionManager));
        commandsMap.put("help", new Help(console, this));
        commandsMap.put("info", new Info(console, collectionManager));
        commandsMap.put("max_by_best_album", new MaxByBestBestAlbum(console, collectionManager));
        commandsMap.put("print_field_ascending_establishment_date", new PrintFieldAscendingEstablishmentDate(console, collectionManager));
        commandsMap.put("remove_by_id", new RemoveByID(console, collectionManager));
        commandsMap.put("show", new Show(console, collectionManager));
    }
}
