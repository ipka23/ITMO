package server_commands;

import common_entities.MusicBand;
import common_utility.network.Response;
import server_managers.CollectionManager;
import server_utility.Command;
import server_utility.interfaces.Console;

import java.util.Collection;

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
     * @param command команда введенная пользователем
     * @return объект utility.ExecutionResponse, содержащий результат выполнения команды
     */
    @Override
    public Response execute(String[] command) {
        if (command[1].trim().trim().isEmpty())
            return new Response(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
        StringBuilder s = new StringBuilder();
        Collection<MusicBand> collection = collectionManager.getCollection();
        for (MusicBand band : collection) {
            if (band.getName().startsWith(command[1].trim())) {
                s.append(band).append("\n");
            }
        }
        if (s.isEmpty())
            return new Response(false, "Нет MusicBands у которых имя начинается с " + command[1] + "!");
        return new Response(false, s.substring(0, s.length() - 1));
    }
}