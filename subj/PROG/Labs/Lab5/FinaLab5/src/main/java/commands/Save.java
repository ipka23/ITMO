package commands;

import managers.CollectionManager;
import utility.Command;
import utility.ExecutionResponse;

public class Save extends Command {
    private CollectionManager collectionManager;
    public Save(CollectionManager collectionManager) {
        super("save", "сохранить коллекцию в файл");
        this.collectionManager = collectionManager;
    }
    @Override
    public ExecutionResponse execute(String[] command) {
        if (!command[1].isEmpty())
            return new ExecutionResponse(false, "Неверное количество аргументов!\nИспользование: \"" + getName() + "\"");
        collectionManager.saveCollection();
        return new ExecutionResponse(true, "Коллекция была успешно сохранена в файл!");
    }
}
