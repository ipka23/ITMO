package utility;

import commands.Command;
import managers.CollectionManager;
import managers.CommandManager;
import models.CollectionType;

import java.util.NoSuchElementException;


/**
 * Данный класс отвечает за запуск консоли и выполнение команд, предоставленных пользователем;
 * Он взаимодействует с менеджером команд(CommandManager) через интерфейс консоли(Console) для выполнения команд и получения результатов.
 *
 * @author ipka23
 */
public class Runner {
    private final Console CONSOLE;
    private final CommandManager COMMAND_MANAGER;
    private final CollectionManager COLLECTION_MANAGER;

    /**
     * Конструктор
     *
     * @param console        интерфейс Console для взаимодействия с консолью
     * @param commandManager менеджер команд CommandManager для управления командами
     */
    public Runner(Console console, CommandManager commandManager, CollectionManager collectionManager) {
        this.CONSOLE = console;
        this.COMMAND_MANAGER = commandManager;
        this.COLLECTION_MANAGER = collectionManager;
    }

    /**
     * Метод для запуска интерактивного режима работы с консолью
     */
    public void interactiveMode() {
        COLLECTION_MANAGER.chooseTypeOfCollection();
        try {
            ExecutionResponse commandStatus;
            String[] userCommand = {"", ""};
            while (true) {
                CONSOLE.printPrompt();
                userCommand = (CONSOLE.nextLine() + " ").split(" ", 2);
                userCommand[0] = userCommand[0].toLowerCase().trim();
                userCommand[1] = userCommand[1].toLowerCase().trim();
                if (userCommand[0].equals("exit")) break;
                if (userCommand[0].isEmpty()) continue;
                commandStatus = run(userCommand);
                CONSOLE.println(commandStatus.getMessage());
            }
        } catch (NoSuchElementException e) {}
    }


    /**
     * Выполняет команду, предоставленную пользователем, и возвращает результат выполнения
     *
     * @param userCommand - массив строк, состоящий из команды и её аргументов
     * @return статус выполнения ExecutionResponse, содержащий результат выполнения команды
     */
    public ExecutionResponse run(String[] userCommand) {
        if (userCommand[0].isEmpty()) return new ExecutionResponse(true, "");
        Command command = COMMAND_MANAGER.getCommandsMap().get(userCommand[0]);
        if (!COMMAND_MANAGER.getCommandsMap().containsKey(userCommand[0])) return new ExecutionResponse(true, "Команда \"" + userCommand[0] + "\" не найдена. Наберите \"help\" для справки");
        return command.execute(userCommand);
    }
}