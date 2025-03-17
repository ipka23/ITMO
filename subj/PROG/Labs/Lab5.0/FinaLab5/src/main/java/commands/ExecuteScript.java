package commands;

import utility.Command;
import utility.ExecutionResponse;
import utility.Invoker;
import utility.exceptions.RecursionDepthExceedException;
import utility.exceptions.ScriptExecutionException;
import utility.interfaces.Console;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class ExecuteScript extends Command {
    private Console console;
    private final Invoker invoker;
    private static final int MAX_RECURSION_DEPTH = 2;

    public ExecuteScript(Console console, Invoker invoker) {
        super("execute_script file_name", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме");
        this.console = console;
        this.invoker = invoker;
    }

    public ExecutionResponse runScript(String fileName, int currentDepth) {
        if (currentDepth > MAX_RECURSION_DEPTH) throw new RecursionDepthExceedException();
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
                scriptFileContent.append(response.getMessage()).append("\n");

                if (command[0].equals("execute_script")) {
                    runScript(command[1], currentDepth + 1);
                }

                if (command[0].equals("exit")) break;
            }
        } catch (FileNotFoundException e) {
            console.setScanner(new Scanner(System.in));
            return new ExecutionResponse(false, "Файл: \"" + fileName + "\" не найден!");
        } catch (RecursionDepthExceedException | ScriptExecutionException e) {
            console.setScanner(new Scanner(System.in));
            return new ExecutionResponse(false, "\n" + e.getMessage());
        }
        console.setScanner(new Scanner(System.in));
        return new ExecutionResponse(true, scriptFileContent.toString());
    }

    @Override
    public ExecutionResponse execute(String[] command) {
        if (command.length != 2 || command[1].trim().isEmpty()) {
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
        }

        return runScript(command[1].trim(), 0);
    }
}