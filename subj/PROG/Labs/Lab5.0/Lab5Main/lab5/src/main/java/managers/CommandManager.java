package managers;

import commands.Command;

import java.util.*;

/**
 * Данный класс отвечает за управление командами
 *
 * @author ipka23
 */
public class CommandManager {
    private final Map<String, Command> commands = new LinkedHashMap<>();

    /**
     * Метод для сохранения команды в словарь-коллекцию команд
     *
     * @param name    имя команды
     * @param command объект Command
     */
    public void addCommand(String name, Command command) {
        commands.put(name, command);
    }

    /**
     * Метод для получения коллекции всех команд
     *
     * @return коллекция всех команд
     */
    public Collection<Command> getCommands() {
        return commands.values();
    }

    /**
     * Метод для получения словаря команд
     *
     * @return словарь всех команд
     */
    public Map<String, Command> getCommandsMap() {
        return commands;
    }


}
