package commands;

import managers.CollectionManager;
import models.MusicBand;
import utility.Console;
import utility.ExecutionResponse;

import java.util.*;

public class PrintFieldAscendingEstablishmentDate extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public PrintFieldAscendingEstablishmentDate(Console console, CollectionManager collectionManager) {
        super("print_field_ascending_establishment_date", "вывести значения поля establishmentDate всех элементов в порядке возрастания");
        this.console = console;
        this.collectionManager = collectionManager;
    }
    @Override
    public ExecutionResponse execute(String[] args) {
        if (!args[1].trim().isEmpty()) return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
        HashSet<MusicBand> collection = collectionManager.getCollection();
        StringBuilder s = new StringBuilder();
        List<Date> dates = new ArrayList<>();
        for (MusicBand band : collection) {
            dates.add(band.getEstablishmentDate());
        }
        Collections.sort(dates);
        for (Date date : dates) {
            s.append(date.toString()).append("\n");
        }
        if (s.isEmpty()) return new ExecutionResponse(true, "Коллекция пуста!");
        return new ExecutionResponse(true, s.substring(0, s.length() - 1));


    }
}
