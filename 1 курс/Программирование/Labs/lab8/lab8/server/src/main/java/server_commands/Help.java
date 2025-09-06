package server_commands;

import common_utility.localization.LanguageManager;
import common_utility.network.Response;
import server_managers.CommandManager;
import server_utility.Command;
import server_utility.consoles.ClientConsole;
import server_utility.interfaces.Console;

/**
 * Данный класс отвечает за выполнение команды "help"
 *
 * @author ipka23
 */
public class Help extends Command {
    private final ClientConsole console;
    private final CommandManager commandManager;

    /**
     * Конструктор
     *
     * @param console        интерфейс Console для взаимодействия с консолью
     * @param commandManager объект CommandManager для управления командами
     */
    public Help(ClientConsole console, CommandManager commandManager) {
        super(LanguageManager.getBundle().getString("help"), LanguageManager.getBundle().getString("helpDescription"));
        this.console = console;
        this.commandManager = commandManager;
    }


    @Override
    public Response execute(String[] command) {
        StringBuilder s = new StringBuilder();
        s.append(LanguageManager.getBundle().getString("helpMessage") + "\n");
        for (Command userCommand : CommandManager.commandsMap.values()) {
            s.append(userCommand.getName()).append(": ").append(userCommand.getDescription()).append("\n");
        }
        return new Response(true, s.substring(0, s.length() - 1));
    }
}