package server_commands;

import common_utility.localization.LanguageManager;
import common_utility.network.Request;
import common_utility.network.Response;
import server_managers.CollectionManager;
import server_utility.Command;
import server_utility.consoles.ClientConsole;
import server_utility.interfaces.Console;

import java.io.IOException;

/**
 * Данный класс отвечает за выполнение команды "info"
 *
 * @author ipka23
 */
public class Info extends Command {
    private final ClientConsole console;
    private final CollectionManager collectionManager;

    /**
     * Конструктор
     *
     * @param console           интерфейс Console для взаимодействия с консолью
     * @param collectionManager объект CollectionManager для управления коллекцией
     */
    public Info(ClientConsole console, CollectionManager collectionManager) {
        super(LanguageManager.getBundle().getString("info"), LanguageManager.getBundle().getString("infoDescription"));
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String[] command) {
        return new Response(true, collectionManager.info());
    }

}