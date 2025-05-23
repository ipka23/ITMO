package server_commands;

import common_utility.network.Request;
import common_utility.network.Response;
import server_managers.CollectionManager;
import server_utility.Command;
import server_utility.interfaces.Console;

import java.io.IOException;

/**
 * Данный класс отвечает за выполнение команды "info"
 *
 * @author ipka23
 */
public class Info extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    /**
     * Конструктор
     *
     * @param console           интерфейс Console для взаимодействия с консолью
     * @param collectionManager объект CollectionManager для управления коллекцией
     */
    public Info(Console console, CollectionManager collectionManager) {
        super("info", "вывести в стандартный поток вывода информацию о коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String[] command) {
        if (!command[1].trim().isEmpty())
            return new Response(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
        return new Response(false, collectionManager.info());
    }

    @Override
    public Response execute(String[] command, Request request) throws IOException, ClassNotFoundException {
        return null;
    }
}