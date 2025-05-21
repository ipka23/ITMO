package server_utility;

import common_utility.network.Response;
import server_managers.CommandManager;
import server_utility.interfaces.Console;
import server_utility.interfaces.Executable;

import java.io.IOException;

// CommandManager, Console
public class Invoker implements Executable {
    private CommandManager commandManager;
    private Console console;

    public Invoker(CommandManager commandManager, Console console) {
        this.commandManager = commandManager;
        this.console = console;
    }


    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public void setConsole(Console console) {
        this.console = console;
    }


    @Override
    public Response execute(String[] command) throws IOException, ClassNotFoundException {
        if (commandManager.getCommand(command[0].trim()) == null) {
            return new Response(false, "Команда \"" + command[0].trim() + "\" не найдена!\nНаберите \"help\" для справки!");
        }
        return commandManager.getCommand(command[0]).execute(command);
    }
}
