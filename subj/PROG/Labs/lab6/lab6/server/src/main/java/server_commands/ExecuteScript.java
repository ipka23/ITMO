package server_commands;

import common_utility.exceptions.ExitClientException;
import common_utility.network.Request;
import common_utility.network.Response;
import server_utility.Command;
import server_utility.Invoker;
import server_utility.consoles.StandartConsole;
import server_utility.exceptions.RecursionDepthExceedException;
import server_utility.exceptions.ScriptExecutionException;

import java.io.*;
import java.util.Scanner;

public class ExecuteScript extends Command {
    private StandartConsole console;
    private final Invoker invoker;
    private static final int MAX_RECURSION_DEPTH = 3;
    private static int currentDepth = -1;
    private String scriptName;
    private ObjectInputStream inFromClient;
    private ObjectOutputStream outToClient;
    private Request request;

    public ExecuteScript(StandartConsole console, Invoker invoker, ObjectInputStream inFromClient, ObjectOutputStream outToClient) throws RecursionDepthExceedException {
        super("execute_script file_name", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме");
        this.console = console;
        this.invoker = invoker;
        this.inFromClient = inFromClient;
        this.outToClient = outToClient;
    }

    public Response runScript(File scriptFile) {
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

                String[] command = (line + " ").split(" ", 2);

                Response response = invoker.execute(command);

                if (response == null) {
                    console.setScanner(new Scanner(System.in));
                    throw new ScriptExecutionException();
                }
                scriptFileContent.append(response.getMessage());
//                if (command[0].equals("execute_script")) {
//                    if (scriptName.equals(command[1])) throw new RecursionDepthExceedException();
//                    currentDepth++;
//                    runScript(command[1]);
//                }
            }
        } catch (FileNotFoundException e) {
            console.setScanner(new Scanner(System.in));
            return new Response(false, "Файл: \"" + scriptFile.getName() + "\" не найден!");
        } catch (RecursionDepthExceedException | ScriptExecutionException e) {
            console.setScanner(new Scanner(System.in));
            return new Response(false, "\n" + e.getMessage());
        } catch (ExitClientException e) {
            return new Response(true, "\n" + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        console.setScanner(new Scanner(System.in));
        return new Response(false, scriptFileContent.substring(0, scriptFileContent.length() - 1));
    }

    public File recieveScriptFile(String fileName) throws IOException {
        int bytes = 0;


        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
//            long size = inFromClient.readLong();
//            byte[] buffer = new byte[1024 * 1024];
//            while (size > 0 && (bytes = inFromClient.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
//                // Here we write the file using write method
//                fileOutputStream.write(buffer, 0, bytes);
//                size -= bytes; // read upto file size
//            }
            while (inFromClient != null) {
                bufferedWriter.write((String) inFromClient.readObject());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return new File(fileName);
    }

    @Override
    public Response execute(String[] command) throws IOException, ClassNotFoundException {
        if (command[1].trim().isEmpty()) {
            return new Response(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
        }
//        request = (Request) inFromClient.readObject();
//        String fileName = request.getScriptFile().getName();
        File scriptFile = recieveScriptFile("tempFile");
        return runScript(scriptFile);
    }
}