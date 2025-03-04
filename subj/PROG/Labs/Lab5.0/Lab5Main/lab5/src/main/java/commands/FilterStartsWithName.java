package commands;

import managers.CollectionManager;
import models.MusicBand;
import utility.Console;
import utility.ExecutionResponse;

import java.util.HashSet;

/**
 * Данный класс отвечает за выполнение команды "filter_starts_with_name"
 *
 * @author ipka23
 */
public class FilterStartsWithName extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    /**
     * Конструктор
     *
     * @param console           интерфейс Console для взаимодействия с консолью
     * @param collectionManager объект CollectionManager для управления коллекцией
     */
    public FilterStartsWithName(Console console, CollectionManager collectionManager) {
        super("filter_starts_with_name name", "вывести элементы, значение поля name которых начинается с заданной подстроки");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Метод для выполнения команды
     *
     * @param args аргументы команды
     * @return объект ExecutionResponse, содержащий результат выполнения команды
     */
    @Override
    public ExecutionResponse execute(String[] args) {
        if (args[1].trim().trim().isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
        StringBuilder s = new StringBuilder();
        HashSet<MusicBand> collection = collectionManager.getCollection();
        for (MusicBand band : collection) {
            if (band.getName().startsWith(args[1].trim())) {
                s.append(band).append("\n");
            }
        }
        if (s.isEmpty())
            return new ExecutionResponse(true, "Нет MusicBands у которых имя начинается с " + args[1] + "!");
        return new ExecutionResponse(true, s.substring(0, s.length() - 1));
    }
}
