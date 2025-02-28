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

    private boolean checkRecursionDepth(String file_name, Scanner scriptScanner) {
        int startDepth = -1;
        int i = 0;
        for (String script_file : scriptList) {

            if (file_name.equals(script_file)) {
                if (startDepth == -1) startDepth = i;
                if (recursionDepth < 0) {
                    console.selectConsoleScanner();
                    console.println("Recursion depth: " + recursionDepth);
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
                    commandStatus = new ExecutionResponse(true, "Превышена максимальная глубина рекурсии!");
                }
                console.println(commandStatus.getMessage());
                if (!commandStatus.getExitStatus()) break;
            } while (commandStatus.getExitStatus() && !commandStatus.getMessage().equals("exit") && console.hasNextLine());


            console.selectConsoleScanner();
            if (!commandStatus.getExitStatus() && !(userCommand[0].equals("execute_script") && !userCommand[1].isEmpty())) {
                executionResponseMessage.append("Ошибка в скрипте!").append("\n");
            }

            return new ExecutionResponse(commandStatus.getExitStatus(), executionResponseMessage.substring(0, executionResponseMessage.length() - 1));

        } catch (NoSuchElementException | FileNotFoundException | IllegalStateException e) {
            console.printError("Ошибка!");
            System.exit(0);
        }
        return new ExecutionResponse(true, "");
    }

    @Override
    public ExecutionResponse execute(String[] args) {

        String scriptFile = args[1].trim();
        if (args[1].trim().isEmpty())  return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
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

// if (args[0].equals("execute_script")) {
//ExecutionResponse response1 = commandManager.getCommandsMap().get("execute_script").execute(args);
//            if (!response1.getExitStatus()) return response1;
//ExecutionResponse response2 = runScript(args[1]);
//        }