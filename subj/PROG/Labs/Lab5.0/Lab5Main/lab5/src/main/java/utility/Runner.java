package utility;

import commands.Command;
import commands.Help;
import managers.CommandManager;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Runner {
    private Console console;
    private final CommandManager commandManager;


    public Runner(Console console, CommandManager commandManager) {
        this.console = console;
        this.commandManager = commandManager;
    }

    public void interactiveMode() {
        try {
            ExecutionResponse commandStatus;
            String[] userCommand = {"", ""};
            while (true) {
                console.printPrompt();
                userCommand = (console.readln() + " ").split(" ", 2);
                userCommand[0] = userCommand[0].toLowerCase().trim();
                userCommand[1] = userCommand[1].trim();
                if (userCommand[0].equals("exit")) break;
                if (userCommand[0].isEmpty()) continue;
                commandStatus = run(userCommand);
                console.println(commandStatus.getMessage());
            }
        } catch (NoSuchElementException e) {
            console.printError(e.getMessage());
        }
    }



    public ExecutionResponse run(String[] userCommand) {
        if (userCommand[0].isEmpty()) return new ExecutionResponse(true, "");
        Command command = commandManager.getCommandsMap().get(userCommand[0]);
        if (command == null)
            return new ExecutionResponse(true, "Команда \"" + userCommand[0] + "\" не найдена. Наберите \"help\" для справки");
        return command.execute(userCommand);
    }
}
