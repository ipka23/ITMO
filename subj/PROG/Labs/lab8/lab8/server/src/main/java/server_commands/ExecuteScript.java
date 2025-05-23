package server_commands;

import common_entities.Album;
import common_entities.Coordinates;
import common_entities.MusicBand;
import common_entities.MusicGenre;
import common_utility.network.Request;
import common_utility.network.Response;
import server_managers.CollectionManager;
import server_utility.Command;
import server_utility.Invoker;
import server_utility.consoles.ClientConsole;
import server_utility.exceptions.RecursionDepthExceedException;

import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;

public class ExecuteScript extends Command {
    private ClientConsole console;
    private final Invoker invoker;
    private static final int MAX_RECURSION_DEPTH = 3;
    private static int currentDepth = -1;
    private ObjectInputStream inFromClient;
    private ObjectOutputStream outToClient;
    private Request request;
    private CollectionManager collectionManager;
    private String innerFile;


    public ExecuteScript(ClientConsole console, Invoker invoker, CollectionManager collectionManager, ObjectInputStream inFromClient, ObjectOutputStream outToClient) throws RecursionDepthExceedException {
        super("execute file_name", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме");
        this.console = console;
        this.invoker = invoker;
        this.inFromClient = inFromClient;
        this.outToClient = outToClient;
//        collectionManager = console.getCollectionManager();
        this.collectionManager = collectionManager;
    }

    public Response runScript(File scriptFile) {
        console.setScriptMode(true);
        console.setScriptFile(scriptFile);
        currentDepth++;
        if (currentDepth / 2 > MAX_RECURSION_DEPTH) {
            currentDepth = 0;
            throw new RecursionDepthExceedException();
        }

        StringBuilder scriptFileContent = new StringBuilder();
        scriptFileContent.append("=================================\n: История выполнения скрипта \"").append(scriptFile.getName()).append("\":\n=================================\n");

        try (Scanner fileScanner = new Scanner(new FileReader(scriptFile))) {
            console.setScanner(fileScanner);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) continue;
                scriptFileContent.append("КОМАНДА: ").append(line).append("\n");


                Response executionResponse;
                String[] command = (line + " ").split(" ", 2);
                if (line.startsWith("execute")) {
                    innerFile = line.split(" ")[1];
                    scriptFileContent.append(runScript(new File(innerFile)).getMessage()).append("\n");
                }
                if (line.startsWith("add")){
                    executionResponse = executeAdd(fileScanner, line);
                } else {
                    executionResponse = invoker.execute(command);
                }
                scriptFileContent.append(executionResponse.getMessage()).append("\n");

            }
        } catch (RecursionDepthExceedException e) {
            return new Response(false, "\n" + e.getMessage());
        } /*catch (ExitClientException e) {
            return new Response(true, "\n" + e.getMessage());
        }*/ catch (IOException | ClassNotFoundException e) {
            return new Response(false, "Непредвиденная ошибка!");
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

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new File(fileName);
    }

    private Response executeAdd(Scanner fileScanner, String line) {
        Response executionResponse = null;
        try {
            String name = fileScanner.nextLine().trim();

            int x = Integer.parseInt(fileScanner.nextLine().trim());

            float y = Float.parseFloat(fileScanner.nextLine().trim());

            long numberOfParticipants = Long.parseLong(fileScanner.nextLine().trim());

            long singlesCount = Long.parseLong(fileScanner.nextLine().trim());

            LocalDate establishmentDate = LocalDate.parse(fileScanner.nextLine().trim());

            MusicGenre musicGenre = MusicGenre.valueOf(fileScanner.nextLine().trim().toUpperCase());

            String albumName = fileScanner.nextLine().trim();

            long tracks = Long.parseLong(fileScanner.nextLine().trim());

            long length = Long.parseLong(fileScanner.nextLine().trim());

            double sales = Double.parseDouble(fileScanner.nextLine().trim());

            Album album = new Album(albumName, tracks, length, sales);
            Coordinates coordinates = new Coordinates(x, y);
            String owner = collectionManager.getDatabaseManager().getUser().getUsername();
            MusicBand musicBand = new MusicBand(owner, name, coordinates, numberOfParticipants, singlesCount, establishmentDate, musicGenre, album);

            if (line.equals("add")) executionResponse =  collectionManager.addMusicBand(musicBand);
            else if (line.equals("add_if_min")) executionResponse =  collectionManager.addMusicBandIfMin(musicBand);
            else if (line.equals("add_if_max")) executionResponse = collectionManager.addMusicBandIfMax(musicBand);

        } catch (Exception e) {
            return new Response(false, "Ошибка при выполнении команды add!");
        }
        return executionResponse;
    }


    @Override
    public Response execute(String[] command) throws IOException, ClassNotFoundException {
        if (command[1].trim().isEmpty()) {
            return new Response(false, "Неправильное количество аргументов!\nИспользование: \"" + getName() + "\"");
        }
        request = (Request) inFromClient.readObject();
        if (!request.isFlag()) return new Response(false, "Файл \"" + request.getFileName() + "\" не найден!");
        File scriptFile = recieveScriptFile(request.getFileName());
        return runScript(scriptFile);
    }
}