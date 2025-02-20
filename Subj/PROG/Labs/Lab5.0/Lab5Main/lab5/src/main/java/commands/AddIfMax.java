package commands;

import managers.CollectionManager;
import models.Ask;
import models.MusicBand;
import utility.Console;
import utility.ExecutionResponse;

public class AddIfMax extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public AddIfMax(Console console, CollectionManager collectionManager) {
        super("add_if_max", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse execute(String[] args) {
        try {
            if (!args[1].isEmpty()) return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
            long id = collectionManager.getFreeId();
            MusicBand newBand = Ask.askMusicBand(console, id);
            if (newBand.getSales() > collectionManager.getMax().getSales()) {
                collectionManager.add(newBand);
                return new ExecutionResponse(true, "В коллекцию был добавлен элемент album.sales которого превышают элемент с максимальным album.sales!");
            }
            else {
                collectionManager.removeByID(id);
                return new ExecutionResponse(true, "Элемент не был добавлен в коллекцию, т. к. его album.sales не превышают элемент с максимальным album.sales!");
            }
        } catch (Ask.AskBreak e) {
            return new ExecutionResponse(true, "Отмена ввода...");
        }
    }
}
