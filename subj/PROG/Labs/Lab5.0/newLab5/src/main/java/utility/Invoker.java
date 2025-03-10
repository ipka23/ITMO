package utility;

import managers.CommandManager;
import utility.interfaces.Executable;


/**
 * Данный класс отвечает за запуск консоли и выполнение команд, предоставленных пользователем;
 * Он взаимодействует с менеджером команд(CommandManager) через интерфейс консоли(Console) для выполнения команд и получения результатов.
 *
 * @author ipka23
 */
public class Invoker implements Executable {
    private final CommandManager COMMAND_MANAGER;

    /**
     * Конструктор
     *
     * @param commandManager менеджер команд CommandManager для управления командами
     */
    public Invoker(CommandManager commandManager) {
        this.COMMAND_MANAGER = commandManager;
    }


    public CommandManager getCOMMAND_MANAGER() {
        return COMMAND_MANAGER;
    }

    /**
     * Выполняет команду, предоставленную пользователем, и возвращает результат выполнения
     *
     * @param userCommand - массив строк, состоящий из команды и её аргументов
     * @return статус выполнения ExecutionResponse, содержащий результат выполнения команды
     */
    @Override
    public ExecutionResponse execute(String[] userCommand) {
        COMMAND_MANAGER.updateHistory(userCommand[0]);
        Command command = COMMAND_MANAGER.getCommandsMap().get(userCommand[0].toLowerCase().trim());
        COMMAND_MANAGER.addToHistory(userCommand);
        if (!COMMAND_MANAGER.getCommandsMap().containsKey(userCommand[0])) return new ExecutionResponse(true, "Команда \"" + userCommand[0] + "\" не найдена. Наберите \"help\" для справки");
        return command.execute(userCommand);
    }

}