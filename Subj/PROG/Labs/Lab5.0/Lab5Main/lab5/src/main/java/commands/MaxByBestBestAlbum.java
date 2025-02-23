package commands;

import managers.CollectionManager;
import models.MusicBand;
import utility.Console;
import utility.ExecutionResponse;

public class MaxByBestBestAlbum extends Command{
    private final Console console;
    private final CollectionManager collectionManager;
    public MaxByBestBestAlbum(Console console, CollectionManager collectionManager) {
        super("max_by_best_album", "вывести любой объект из коллекции, значение поля bestAlbum которого является максимальным");
        this.console = console;
        this.collectionManager = collectionManager;
    }
    @Override
    public ExecutionResponse execute(String[] args) {
        if (!args[1].trim().isEmpty()) return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        MusicBand bestBand;
        bestBand = collectionManager.getMax();
        StringBuilder s = new StringBuilder();
        s.append("MusicBand с максимальным количеством album.sales:").append("\n");
        s.append(bestBand.toString());
        return new ExecutionResponse(true, s.toString());
    }
}
