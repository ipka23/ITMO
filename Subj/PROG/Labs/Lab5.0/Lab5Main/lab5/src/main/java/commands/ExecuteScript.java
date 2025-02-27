package commands;

import managers.CollectionManager;
import managers.CommandManager;
import utility.Console;
import utility.ExecutionResponse;
import utility.Runner;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ExecuteScript extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    private final Runner runner;
    private final List<String> scriptList = new ArrayList<>();
    private int recursionDepth = -1;

    public ExecuteScript(Console console, CollectionManager collectionManager, CommandManager commandManager, Runner runner) {
        super("execute_script file_name", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме");
        this.console = console;
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
        this.runner = runner;
    }

    private boolean checkRecursionDepth(String fileName, Scanner scriptScanner) {
        int startDepth = -1;
        int i = 0;
        for (String scriptFile : scriptList) {
            if (fileName.equals(scriptFile)) {
                if (startDepth == -1) startDepth = i;
                if (recursionDepth < 0) {
                    console.selectConsoleScanner();
                    console.selectFileScanner(scriptScanner);
                }
                if (i > startDepth + recursionDepth || i > 500) return false;
            }
            i++;
        }
        return true;
    }

    public ExecutionResponse runScript(String filename) {
        String[] userCommand = {"", ""};
        StringBuilder executionResponseMessage = new StringBuilder();
        try (Scanner scriptFileScanner = new Scanner(new File(filename))) {
            ExecutionResponse commandStatus = null;
            if (!scriptFileScanner.hasNextLine()) throw new NoSuchElementException();
            console.selectFileScanner(scriptFileScanner);

            do {
                boolean shouldExecuteCommand = true;
                userCommand = (console.readln().trim() + " ").split(" ", 2);
                userCommand[0] = userCommand[0].toLowerCase().trim();
                userCommand[1] = userCommand[1].trim();
                if (userCommand[0].equals("exit")) break;
                if (userCommand[0].isEmpty()) continue;
                executionResponseMessage.append(console.getScriptPrompt()).append(userCommand[0]).append(" ").append(userCommand[1]).append("\n");
                if (userCommand[0].equals("execute_script")) {
                    shouldExecuteCommand = checkRecursionDepth(userCommand[1], scriptFileScanner);
                }
                if (shouldExecuteCommand) {
                    commandStatus = runner.run(userCommand);
                } else {
                    commandStatus = new ExecutionResponse(false, "Превышена максимальная глубина рекурсии!");
                }
                console.println(commandStatus.getMessage());
                if (!commandStatus.getExitStatus()) break;
            } while (scriptFileScanner.hasNextLine());

            console.selectConsoleScanner();
            if (commandStatus != null && !commandStatus.getExitStatus() && !(userCommand[0].equals("execute_script") && !userCommand[1].isEmpty())) {
                executionResponseMessage.append("Ошибка в скрипте!").append("\n");
            }
            return new ExecutionResponse(commandStatus != null && commandStatus.getExitStatus(), executionResponseMessage.toString().trim());
        } catch (NoSuchElementException | FileNotFoundException | IllegalStateException e) {
            console.printError("Ошибка!");
            return new ExecutionResponse(false, "Ошибка при выполнении скрипта");
        }
    }

    @Override
    public ExecutionResponse execute(String[] args) {
        if (args.length < 2 || args[1].trim().isEmpty()) {
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        }
        String scriptFile = args[1].trim();
        if (!new File(scriptFile).exists()) {
            return new ExecutionResponse(false, "Файл не найден!");
        }
        if (!Files.isReadable(Paths.get(scriptFile))) {
            return new ExecutionResponse(false, "execute_script: " + scriptFile + ": Permission denied");
        }

        scriptList.add(scriptFile);
        ExecutionResponse executionResponse = runScript(scriptFile);
        scriptList.remove(scriptList.size() - 1);
        return executionResponse;
    }
}