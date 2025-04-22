package server_commands;

import common_utility.network.Response;
import org.slf4j.Logger;
import server_managers.CollectionManager;
import server_utility.Command;

/**
 * Данный класс отвечает за выполнение команды "exit"
 *
 * @author ipka23
 */
public class Exit extends Command {
    private final CollectionManager collectionManager;
    private Logger log;
    /**
     * Конструктор
     */
    public Exit(CollectionManager collectionManager, Logger log) {
        super("exit", "завершить программу (без сохранения в файл)");
        this.collectionManager = collectionManager;
        this.log = log;
    }


    @Override
    public Response execute(String[] command) {
        collectionManager.saveCollection();
        log.info("Client disconnected!");
        return new Response(true, "Завершение работы клиента...");
    }
}