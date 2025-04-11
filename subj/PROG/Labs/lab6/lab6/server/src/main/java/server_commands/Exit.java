package server_commands;

import common_utility.network.Response;
import server_managers.CollectionManager;
import server_utility.Command;

/**
 * Данный класс отвечает за выполнение команды "exit"
 *
 * @author ipka23
 */
public class Exit extends Command {
    CollectionManager collectionManager;
    /**
     * Конструктор
     */
    public Exit(CollectionManager collectionManager) {
        super("exit", "завершить программу (без сохранения в файл)");
        this.collectionManager = collectionManager;
    }


    @Override
    public Response execute(String[] command) {
        collectionManager.saveCollection();
        return new Response(true, "Завершение работы клиента...");
    }
}