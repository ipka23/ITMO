package commands;

import managers.CommandManager;
import utility.Console;
import utility.ExecutionResponse;

public class History extends Command {
    private final Console CONSOLE;
    private final CommandManager COMMAND_MANAGER;

    public History(Console console, CommandManager commandManager) {
        super("history", "вывести историю команд");
        this.CONSOLE = console;
        this.COMMAND_MANAGER = commandManager;
    }

    @Override
    public ExecutionResponse execute(String[] args) {
        StringBuilder s = new StringBuilder();
        if (!args[1].isEmpty()) return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
        s.append("--------------------------------История ввода--------------------------------\n");
        for (int i = 0; i < COMMAND_MANAGER.getHistory().size(); i++) {
            s.append(COMMAND_MANAGER.getHistory().get(i)).append("\n");
        }
        return new ExecutionResponse(true, s.substring(0, s.length() - 1));
    }
}
