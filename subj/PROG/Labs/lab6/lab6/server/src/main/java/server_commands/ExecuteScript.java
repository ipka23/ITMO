package server_commands;

import common_utility.ExecutionResponse;
import common_utility.exceptions.ExitClientException;
import server_utility.Command;
import server_utility.Invoker;
import server_utility.exceptions.RecursionDepthExceedException;
import server_utility.exceptions.ScriptExecutionException;
import server_utility.interfaces.Console;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class ExecuteScript extends Command {
    private Console console;
    private final Invoker invoker;
    private static final int MAX_RECURSION_DEPTH = 3;
    private static int currentDepth = -1;
    private String scriptName;

    public ExecuteScript(Console console, Invoker invoker) throws RecursionDepthExceedException {
        super("execute_script file_name", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме");
        this.console = console;
        this.invoker = invoker;
    }

    public ExecutionResponse runScript(String fileName) {
        currentDepth++;
        if (currentDepth / 2 > MAX_RECURSION_DEPTH) {
            currentDepth = 0;
            throw new RecursionDepthExceedException();
        }

        StringBuilder scriptFileContent = new StringBuilder();
        scriptFileContent.append("--------------------------------История выполнения команд из скрипта \"").append(fileName).append("\"--------------------------------\n");
        try (Scanner fileScanner = new Scanner(new FileReader(fileName))) {
            console.setScanner(fileScanner);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                scriptFileContent.append(console.getScriptPrompt()).append(line).append("\n");

                if (line.isEmpty()) continue;

                String[] command = (line + " ").split(" ", 2);

                ExecutionResponse response = invoker.execute(command);

                if (response == null) {
                    console.setScanner(new Scanner(System.in));
                    throw new ScriptExecutionException();
                }
                scriptFileContent.append(response.getMessage());
                if (command[0].equals("execute_script")) {
                    if (scriptName.equals(command[1])) throw new RecursionDepthExceedException();
                    currentDepth++;
                    runScript(command[1]);
                }
            }
        } catch (FileNotFoundException e) {
            console.setScanner(new Scanner(System.in));
            return new ExecutionResponse(false, "Файл: \"" + fileName + "\" не найден!");
        } catch (RecursionDepthExceedException | ScriptExecutionException e) {
            console.setScanner(new Scanner(System.in));
            return new ExecutionResponse(false, "\n" + e.getMessage());
        } catch (ExitClientException e) {
            return new ExecutionResponse(true, "\n" + e.getMessage());
        }
        console.setScanner(new Scanner(System.in));
        return new ExecutionResponse(false, scriptFileContent.substring(0, scriptFileContent.length() - 1));
    }

    @Override
    public ExecutionResponse execute(String[] command) {
        if (command[1].trim().isEmpty()) {
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
        }
        scriptName = command[1].trim();
        return runScript(scriptName);
    }
}