package commands;

import managers.CollectionManager;
import models.Ask;
import models.MusicBand;
import utility.Console;
import utility.ExecutionResponse;

public class Update extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Update(Console console, CollectionManager collectionManager) {
        super("update id", "обновить значение элемента коллекции, id которого равен заданному");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    public ExecutionResponse execute(String[] args) {
        if (args[1].isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        long id;
        try {
            id = Long.parseLong(args[1].trim());
        } catch (NumberFormatException e) {
            return new ExecutionResponse(false, "Неверный формат id!");
        }
        var band = collectionManager.getMusicBandById(id);
        if (band == null || !collectionManager.getCollection().contains(band)) {
            return new ExecutionResponse(false, "В коллекции нет элемента с таким id!");
        }
        MusicBand newBand;
        try {
            console.println("----Введите новые данные для MusicBand----");
            newBand = Ask.askMusicBand(console, id);
        } catch (Ask.AskBreak e) {
            return new ExecutionResponse(true, "Отмена ввода...");
        }
        band.update(newBand);
        return new ExecutionResponse(true, "Элемент с id = " + id + " был обновлён!");
    }
}
