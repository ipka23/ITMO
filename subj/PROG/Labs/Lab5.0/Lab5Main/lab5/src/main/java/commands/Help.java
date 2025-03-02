package commands;

import managers.CommandManager;
import utility.Console;
import utility.ExecutionResponse;

public class Help extends Command {
    private final Console console;
    private final CommandManager commandManager;
    public Help(Console console, CommandManager commandManager) {
        super("help", "вывести справку по доступным командам");
        this.console = console;
        this.commandManager = commandManager;
    }

    @Override
    public ExecutionResponse execute(String[] args) {
        StringBuilder s = new StringBuilder();
        if (!args[1].trim().isEmpty()) return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
        s.append("--------------------------------Доступные команды--------------------------------\n");
        for (Command command : commandManager.getCommands()){
            s.append(command.getName()).append(": ").append(command.getDescription()).append("\n");
        }
        return new ExecutionResponse(true, s.substring(0, s.length() - 1));
    }
}
