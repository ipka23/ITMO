package server_commands;

import common_utility.exceptions.ExitClientException;
import common_utility.network.Request;
import common_utility.network.Response;
import server_utility.Command;
import server_utility.Invoker;
import server_utility.consoles.ClientConsole;
import server_utility.exceptions.RecursionDepthExceedException;

import java.io.*;
import java.util.Scanner;

public class ExecuteScript extends Command {
    private ClientConsole console;
    private final Invoker invoker;
    private static final int MAX_RECURSION_DEPTH = 3;
    private static int currentDepth = -1;
    private ObjectInputStream inFromClient;
    private ObjectOutputStream outToClient;
    private Request request;
    private String scriptContent;


    public ExecuteScript(ClientConsole console, Invoker invoker, ObjectInputStream inFromClient, ObjectOutputStream outToClient) throws RecursionDepthExceedException {
        super("execute_script file_name", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме");
        this.console = console;
        this.invoker = invoker;
        this.inFromClient = inFromClient;
        this.outToClient = outToClient;
    }

    public Response runScript(File scriptFile) {
        console.setScriptMode(true);
        currentDepth++;
        if (currentDepth / 2 > MAX_RECURSION_DEPTH) {
            currentDepth = 0;
            throw new RecursionDepthExceedException();
        }

        StringBuilder scriptFileContent = new StringBuilder();
        scriptFileContent.append("--------------------------------История выполнения команд из скрипта \"").append(scriptFile.getName()).append("\"--------------------------------\n");
        try (Scanner fileScanner = new Scanner(new FileReader(scriptFile))) {
            console.setScanner(fileScanner);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                scriptFileContent.append(console.getScriptPrompt()).append(line).append("\n");

                if (line.isEmpty()) continue;
                try (BufferedReader bufferedReader = new BufferedReader(new FileReader(scriptFile))) { //TODO
                    StringBuilder stringBuilder = new StringBuilder();
                    String s;
                    while ((s = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    stringBuilder = new StringBuilder(stringBuilder.substring(0, stringBuilder.toString().length() - 1));
                    scriptContent = stringBuilder.toString();
                } catch (FileNotFoundException e) {}
                String[] command = (line + " ").split(" ", 2);

                Response response = invoker.execute(command);
//
//                if (response == null) {
//                    console.setScanner(new Scanner(System.in));
//                    throw new ScriptExecutionException();
//                }
                scriptFileContent.append(response.getMessage()).append("\n");

            }
        } catch (FileNotFoundException e) {
            return new Response(false, "Файл: \"" + scriptFile.getName() + "\" не найден!");
        } catch (RecursionDepthExceedException e) {
            return new Response(false, "\n" + e.getMessage());
        } catch (ExitClientException e) {
            return new Response(true, "\n" + e.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            console.setScanner(new Scanner(System.in));
            console.setScriptMode(false);
            scriptFile.delete();
        }
        return new Response(false, scriptFileContent.substring(0, scriptFileContent.length() - 1));
    }

    public File recieveScriptFile(String fileName) {

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {


            bufferedWriter.write(request.getMessage());
            bufferedWriter.newLine();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new File(fileName);
    }

    @Override
    public Response execute(String[] command) throws IOException, ClassNotFoundException {
        if (command[1].trim().isEmpty()) {
            return new Response(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
        }
        request = (Request) inFromClient.readObject();
        File scriptFile = recieveScriptFile(request.getFileName());
//        File scriptFile = request.getScriptFile();
        return runScript(scriptFile);
    }
}