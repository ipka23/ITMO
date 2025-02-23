package commands;

import utility.Console;
import utility.ExecutionResponse;

public class Exit extends Command {
    private final Console console;

    public Exit(Console console) {
        super("exit", "завершить программу (без сохранения в файл)");
        this.console = console;
    }


    @Override
    public ExecutionResponse execute(String[] args) {
        if (!args[1].trim().isEmpty()) return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        return new ExecutionResponse(true);
    }
}