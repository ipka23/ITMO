package server_commands;

import common_entities.MusicBand;
import server_managers.CollectionManager;
import server_utility.Command;
import common_utility.network.ExecutionResponse;
import server_utility.interfaces.Console;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Данный класс отвечает за выполнение команды "print_field_ascending_establishment_date"
 *
 * @author ipka23
 */
public class PrintFieldAscendingEstablishmentDate extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    /**
     * Конструктор
     *
     * @param console           интерфейс Console для взаимодействия с консолью
     * @param collectionManager объект CollectionManager для управления коллекцией
     */
    public PrintFieldAscendingEstablishmentDate(Console console, CollectionManager collectionManager) {
        super("print_field_ascending_establishment_date", "вывести значения поля establishmentDate всех элементов в порядке возрастания");
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
    public ExecutionResponse execute(String[] command) {
        if (!command[1].trim().isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
        Collection<MusicBand> collection = collectionManager.getCollection();
        StringBuilder s = new StringBuilder();
        List<Date> dates = new ArrayList<>();
        for (MusicBand band : collection) {
            dates.add(band.getEstablishmentDate());
        }
        Collections.sort(dates);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        for (Date date : dates) {
            s.append(formatter.format(date)).append("\n");
        }
        if (s.isEmpty()) return new ExecutionResponse(false, "Коллекция пуста!");
        return new ExecutionResponse(false, s.substring(0, s.length() - 1));


    }
}