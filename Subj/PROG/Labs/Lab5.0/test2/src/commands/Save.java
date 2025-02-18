package commands;

import managers.CollectionManager;
import utility.Console;
import utility.ExecutionResponse;

public class Save extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    public Save(Console console, CollectionManager collectionManager) {
        super("save", "сохранить коллекцию в файл");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse execute(String[] args) {
        if (args.length > 1) return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        collectionManager.saveCollection();
        return new ExecutionResponse(true, "Коллекция была сохранена!");
    }
}
