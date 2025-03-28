package utility;

import managers.CommandManager;
import utility.interfaces.Console;
import utility.interfaces.Executable;
// CommandManager, Console
public class Invoker implements Executable {
    private CommandManager commandManager;
    private Console console;
    public Invoker(CommandManager commandManager, Console console) {
        this.commandManager = commandManager;
        this.console = console;
    }

    public Invoker() {
    }

    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public void setConsole(Console console) {
        this.console = console;
    }


    @Override
    public ExecutionResponse execute(String[] command) {
        if (commandManager.getCommand(command[0].trim()) == null) {
            return new ExecutionResponse(false, "Команда \"" + command[0].trim() + "\" не найдена!\nНаберите \"help\" для справки!");
        }
        return commandManager.getCommand(command[0]).execute(command);
    }
}
