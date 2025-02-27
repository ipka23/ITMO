package commands;

import managers.CollectionManager;
import utility.Console;
import utility.ExecutionResponse;

public class Clear extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    public Clear(Console console, CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse execute(String[] args) {
        if (args.length > 1)  return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        collectionManager.getCollection().clear();
        return new ExecutionResponse(true, "Коллекция была очищена!");
    }
}
