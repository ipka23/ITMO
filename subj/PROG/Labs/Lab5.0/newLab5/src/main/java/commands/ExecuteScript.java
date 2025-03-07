package commands;

import managers.CollectionManager;
import managers.CommandManager;
import utility.Command;
import utility.interfaces.Console;
import utility.ExecutionResponse;
import utility.Invoker;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Данный класс отвечает за выполнение команды "execute_script"
 * Он взаимодействует с объектами Console, CollectionManager, CommandManager и Runner для выполнения команд из файла скрипта
 * Скрипты содержат команды в том же виде, в котором они вводятся пользователем в интерактивном режиме
 *
 * @author ipka23
 */
public class ExecuteScript extends Command {
    private final Console CONSOLE;
    private final CollectionManager COLLECTION_MANAGER;
    private final CommandManager COMMAND_MANAGER;
    private Invoker invoker;
    private final List<String> SCRIPT_LIST = new ArrayList<>();
    private int recursionDepth = -1;

    /**
     * Конструктор
     *
     * @param console           интерфейс Console для взаимодействия с консолью
     * @param collectionManager объект CollectionManager для управления коллекцией
     * @param commandManager    менеджер команд CommandManager для управления командами
     * @param invoker            для выполнения команд
     */
    public ExecuteScript(Console console, CollectionManager collectionManager, CommandManager commandManager, Invoker invoker) {
        super("execute_script file_name", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме");
        this.CONSOLE = console;
        this.COLLECTION_MANAGER = collectionManager;
        this.COMMAND_MANAGER = commandManager;
        this.invoker = invoker;
    }

    public void setRunner(Invoker invoker) {
        this.invoker = invoker;
    }

    /**
     * Метод для проверки глубины рекурсии
     *
     * @param file_name имя файла скрипта
     * @return true, если глубина рекурсии не превышена, false в противном случае
     */
    private boolean checkRecursionDepth(String file_name) {
        int startDepth = -1;
        int i = 0;
        for (String script_file : SCRIPT_LIST) {

            if (file_name.equals(script_file)) {
                if (startDepth == -1) startDepth = i;
                if (recursionDepth > 300) {
                    CONSOLE.selectConsoleScanner();
                    CONSOLE.println("Recursion depth: " + startDepth + recursionDepth);
                }
                if (i > startDepth + recursionDepth || i > 300) return false;
            }
            i++;
        }
        return true;
    }

    /**
     * Метод для запуска скрипта
     *
     * @param filename имя файла скрипта
     * @return объект ExecutionResponse, содержащий результат выполнения скрипта
     */
    public ExecutionResponse runScript(String filename) {
        String[] userCommand = {"", ""};
        StringBuilder executionResponseMessage = new StringBuilder();
        executionResponseMessage.append("--------------------------------История выполненных комманд из скрипта: \"").append(filename).append("\"").append("--------------------------------").append("\n");
        try (Scanner scriptFileScanner = new Scanner(new File(filename))) {
            ExecutionResponse commandStatus = null;
            if (!scriptFileScanner.hasNextLine()) throw new NoSuchElementException();
            CONSOLE.selectFileScanner(scriptFileScanner);

            do {
                boolean shouldExecuteCommand = true;
                userCommand = (CONSOLE.nextLine().trim() + " ").split(" ", 2);
                userCommand[0] = userCommand[0].trim();
                userCommand[1] = userCommand[1].trim();
                if (userCommand[0].equals("exit")) break;
                if (userCommand[0].isEmpty()) continue;
                executionResponseMessage.append(CONSOLE.getScriptPrompt()).append(userCommand[0]).append(" ").append(userCommand[1]).append("\n");

                if (userCommand[0].equals("execute_script")) {
                    shouldExecuteCommand = checkRecursionDepth(userCommand[1]);
                }
                if (shouldExecuteCommand) {
                    commandStatus = invoker.execute(userCommand);
                } else {
                    commandStatus = new ExecutionResponse(true, "Превышена максимальная глубина рекурсии!");
                }
                CONSOLE.println(commandStatus.getMessage());
                if (!commandStatus.getExitStatus()) break;

            } while (CONSOLE.hasNextLine());
            if (SCRIPT_LIST.size() > 1) {
                executionResponseMessage.append("Введите \"exit\" чтобы посмотреть историю команд из вложенного скрипта:").append("\n");
                CONSOLE.printPrompt();
            }

            CONSOLE.selectConsoleScanner();

            if (!commandStatus.getExitStatus() && !(userCommand[0].equals("execute_script") && !userCommand[1].isEmpty())) {
                executionResponseMessage.append("Ошибка в скрипте!").append("\n");
            }


            return new ExecutionResponse(commandStatus.getExitStatus(), executionResponseMessage.substring(0, executionResponseMessage.length() - 1));

        } catch (FileNotFoundException exception) {
            return new ExecutionResponse(false, "Файл со скриптом не найден!");
        } catch (NoSuchElementException exception) {
            return new ExecutionResponse(false, "Файл со скриптом пуст!");
        } catch (IllegalStateException exception) {
            return new ExecutionResponse(false, "Непредвиденная ошибка!");
        }
    }

    /**
     * Метод для выполнения команды
     *
     * @param args аргументы команды
     * @return объект ExecutionResponse, содержащий результат выполнения команды
     */
    @Override
    public ExecutionResponse execute(String[] args) {
        String scriptFile = args[1].trim();
        if (args[1].trim().isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
        if (!Files.exists(Paths.get(scriptFile))) {
            return new ExecutionResponse(false, "Файл со скриптом \"" + scriptFile + "\" не найден!");
        }
        if (!Files.isReadable(Paths.get(scriptFile))) {
            return new ExecutionResponse(false, "execute_script: " + scriptFile + ": Permission denied!");
        }
        SCRIPT_LIST.add(scriptFile);
        ExecutionResponse executionResponse = runScript(scriptFile);
        SCRIPT_LIST.remove(SCRIPT_LIST.size() - 1);
        return executionResponse;
    }
}
