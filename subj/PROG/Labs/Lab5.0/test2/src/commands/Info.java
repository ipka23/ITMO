package commands;

import managers.CollectionManager;
import utility.Console;
import utility.ExecutionResponse;

public class Info extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Info(Console console, CollectionManager collectionManager) {
        super("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse execute(String[] args) {
        if (args.length > 1) return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        return new ExecutionResponse(true, collectionManager.toString());

    }
}
