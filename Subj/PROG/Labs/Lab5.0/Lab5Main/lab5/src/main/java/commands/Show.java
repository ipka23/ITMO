package commands;

import managers.CollectionManager;
import utility.Console;
import utility.ExecutionResponse;


public class Show extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    public Show(Console console, CollectionManager collectionManager) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.console = console;
        this.collectionManager = collectionManager;
    }


    @Override
    public ExecutionResponse execute(String[] args) {
            if (!args[1].isEmpty()) return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");

        return new ExecutionResponse(true, collectionManager.toString());
    }
}
