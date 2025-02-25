package utility;

import commands.Command;
import commands.Help;
import managers.CommandManager;

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
                if (userCommand[0].equals("exit")) break;
                if (userCommand[0].isEmpty()) continue;
                commandStatus = run(userCommand);
                console.println(commandStatus.getMessage());
            }
        } catch (NoSuchElementException e) {
            console.printError(e.getMessage());
        }
    }


    public ExecutionResponse runScript(String filename) {
        return null;
    }

    public ExecutionResponse run(String[] userCommand) {
        if (userCommand[0].isEmpty()) return new ExecutionResponse(true, "");
        Command command = commandManager.getCommandsMap().get(userCommand[0]);
        if (command == null)
            return new ExecutionResponse(true, "Команда '" + userCommand[0] + "' не найдена. Наберите 'help' для справки");
        if (userCommand[0].equals("execute_script")) {
            ExecutionResponse response1 = commandManager.getCommandsMap().get("execute_script").execute(userCommand);
            if (!response1.getExitStatus()) return response1;
            ExecutionResponse response2 = runScript(userCommand[1]);
        }
        return command.execute(userCommand);
    }
}
