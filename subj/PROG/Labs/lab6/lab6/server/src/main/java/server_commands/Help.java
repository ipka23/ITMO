package server_commands;

import common_utility.ExecutionResponse;
import server_managers.CommandManager;
import server_utility.Command;
import server_utility.interfaces.Console;

/**
 * Данный класс отвечает за выполнение команды "help"
 *
 * @author ipka23
 */
public class Help extends Command {
    private final Console console;
    private final CommandManager commandManager;

    /**
     * Конструктор
     *
     * @param console        интерфейс Console для взаимодействия с консолью
     * @param commandManager объект CommandManager для управления командами
     */
    public Help(Console console, CommandManager commandManager) {
        super("help", "вывести справку по доступным командам");
        this.console = console;
        this.commandManager = commandManager;
    }

    /**
     * Метод для выполнения команды
     *
     * @param command команда введенная пользователем
     * @return объект utility.ExecutionResponse, содержащий результат выполнения команды
     */
    @Override
    public ExecutionResponse execute(String[] command) {
        StringBuilder s = new StringBuilder();
        if (!command[1].trim().isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
        s.append("--------------------------------Доступные команды--------------------------------\n");
        for (Command userCommand : commandManager.getCommandsMap().values()) {
            s.append(userCommand.getName()).append(": ").append(userCommand.getDescription()).append("\n");
        }
        return new ExecutionResponse(true, s.substring(0, s.length() - 1));
    }
}