package server_utility;

import common_utility.network.Request;
import common_utility.network.Response;
import server_managers.CommandManager;
import server_utility.interfaces.Console;
import server_utility.interfaces.Executable;
import server_utility.interfaces.RExecutable;

import java.io.IOException;

// CommandManager, Console
public class Invoker implements Executable, RExecutable {
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
            return new Response(false);
        }
        return commandManager.getCommand(command[0]).execute(command);
    }

    @Override
    public Response execute(String[] command, Request request) throws IOException, ClassNotFoundException {
        if (commandManager.getCommand(command[0].trim()) == null) {
            return new Response(false);
        }
        Response response;
        RCommand current_command = (RCommand) commandManager.getCommand(command[0]);
        if (current_command.getName().startsWith("add")) {
            response = current_command.execute(command, request);
        } else response = current_command.execute(command);
        return response;
    }
}
