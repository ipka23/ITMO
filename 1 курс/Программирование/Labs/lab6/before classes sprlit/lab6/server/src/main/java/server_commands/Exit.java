package server_commands;

import common_utility.network.Response;
import server_utility.Command;

/**
 * Данный класс отвечает за выполнение команды "exit"
 *
 * @author ipka23
 */
public class Exit extends Command {
    /**
     * Конструктор
     */
    public Exit() {
        super("exit", "завершить программу (без сохранения в файл)");
    }

    /**
     * Метод для выполнения команды
     *
     * @param command команда введенная пользователем
     * @return объект utility.ExecutionResponse, содержащий результат выполнения команды
     */
    @Override
    public Response execute(String[] command) {
        return new Response(true, "Завершение работы клиента...");
    }
}