package network;

import common_entities.MusicBand;
import common_utility.database.User;
import common_utility.exceptions.ExitClientException;
import common_utility.network.Request;
import common_utility.network.Response;
import fx.controllers.SceneController;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;
import java.util.stream.Stream;

@Getter
@Setter
public class RequestSender {
    private static Collection<MusicBand> musicBandsCollection = new HashSet<>();

    private ObjectOutputStream outToServer;
    private ObjectInputStream inFromServer;

    public RequestSender(ObjectOutputStream outToServer, ObjectInputStream inFromServer) throws IOException {
//        authentication(outToServer, inFromServer);
//        sendMessage();
        this.outToServer = outToServer;
        this.inFromServer = inFromServer;
    }
    public void sendMessage() throws IOException {

        Response response;
        Request request;
        String command;
        String arg;
        try {

            // основная программа

            /*while (true) {
                Response prompt = getResponse(inFromServer);
                System.out.print(prompt.getMessage());
                String message = userInput.nextLine().trim();

                command = (message + " ").split(" ", 2)[0].trim().toLowerCase();
                arg = (message + " ").split(" ", 2)[1].trim();

                request = new Request(message);
                sendRequest(request, outToServer);


                if (command.equals("show_scripts")) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("=====================\n: Доступные скрипты :\n=====================\n");
                    try (Stream<Path> files = Files.walk(Path.of("sr/main/resources"))) {
                        files.skip(1).forEach(file -> sb.append(file.getFileName()).append("\n"));
                        System.out.println(sb.substring(0, sb.toString().lastIndexOf("\n")));

                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }

                if (command.equals("execute")) {
                    FileSender.sendScriptFile(arg, outToServer);
                }

                if (message.isEmpty()) continue;
                response = getResponse(inFromServer);
                if (response == null) {
                    continue;
                }
                if (command.equals("show") || command.equals("filter_starts_with_name") || command.equals("max_by_best_album")) {
                    printCollection(response);
                } else if (command.startsWith("add") || command.equals("update") || command.equals("remove_greater")) {
                    if (response.getExitStatus()) {
                        System.out.println(response.getMessage());
                        continue;
                    }
                    System.out.print(response.getMessage());
                    while (true) {
                        String input = userInput.nextLine();
                        sendRequest(new Request(input), outToServer);
                        response = getResponse(inFromServer);
                        if (response.getExitStatus()) {
                            musicBandsCollection = response.getMusicBandsCollection();
                            System.out.println(response.getMessage());
                            break;
                        } else {
                            musicBandsCollection = response.getMusicBandsCollection();
                            System.out.print(response.getMessage());
                        }
                    }
                } else if (response.getExitStatus()*//* && !response.getMessage().equals("Отмена ввода...")*//*) {
                    System.out.print(response.getMessage());
                    System.exit(1);
                } else {
//                    musicBandsCollection = response.getMusicBandsCollection();
                    System.out.println(response.getMessage());
                }

            }*/
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void authentication(ObjectOutputStream outToServer, ObjectInputStream inFromServer) {
        String username = "";
        String password = "";
        try {
            while (true) {
                        sendRequest(new Request("login", new User(username, password)), outToServer);
                        Response response = getResponse(inFromServer);
                        if (!response.getExitStatus()) {
                            System.out.println(response.getMessage());
                            continue;
                        } else {
                            System.out.println(response.getMessage());
                            break;
                        }
//                        sendRequest(new Request("register", new User(username, password)), outToServer);
//                        Response response1 = getResponse(inFromServer);
//                        if (!response.getExitStatus()) {
//                            System.out.println(response.getMessage());
//                        } else {
//                            System.out.println(response.getMessage());
//                        }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void printCollection(Response response) {
        musicBandsCollection = response.getMusicBandsCollection();
        String message = response.getMessage();
        if (message.equals("max_by_best_album")) {
            System.out.println("========================================================================\n: Музыкальная группа с максимальным количеством продаж лучшего альбома :\n========================================================================");
        }
        if (musicBandsCollection != null && !musicBandsCollection.isEmpty()) {
            System.out.printf("|%-15s|%-30s|%-30s|%-30s|%-20s|%n", "ID группы", "Владелец группы", "Название группы", "Лучший альбом", "Количество продаж");
            System.out.println("-" + "-".repeat(15) + "+" + "-".repeat(30) + "+" + "-".repeat(30) + "+" + "-".repeat(30) + "+" + "-".repeat(20) + "-");
            musicBandsCollection
                    .stream()
                    .forEach(band -> System.out.printf(
                            "|%-15s|%-30s|%-30s|%-30s|%-20s|%n",
                            band.getId(),
                            band.getOwner(),
                            band.getName(),
                            band.getBestAlbum().getName(),
                            band.getBestAlbum().getSales())
                    );

        } else {
            System.out.println(response.getMessage());
        }
    }

    public void sendRequest(Request r, ObjectOutputStream outToServer) throws IOException {
        outToServer.writeObject(r);
        outToServer.flush();
    }

    public Response getResponse(ObjectInputStream inFromServer) throws IOException, ClassNotFoundException {
        return (Response) inFromServer.readObject();
    }

}
