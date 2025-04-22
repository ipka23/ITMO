package network;

import common_entities.MusicBand;
import common_utility.network.Request;
import common_utility.network.Response;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;

public class RequestSender {
    private static Collection<MusicBand> musicBandsCollection = new HashSet<>();

    public static void sendMessage(ObjectOutputStream outToServer, ObjectInputStream inFromServer, Scanner userInput) {
        Response response;
        Request request;
        String command;
        String arg;
        try {
            while (true) {
//                Response prompt = inFromServer.readObject() == null ? new Response(false, ">") : (Response) inFromServer.readObject();
//                Response nul = (Response) inFromServer.readObject();
                Response prompt = (Response) inFromServer.readObject();
                if (prompt != null) {
                    System.out.print(prompt.getMessage());
                } else {
                    Response response1 = (Response) inFromServer.readObject();
                    System.out.print(response1.getMessage());
                }

                String message = userInput.nextLine().trim();

                command = (message + " ").split(" ", 2)[0].trim().toLowerCase();
                arg = (message + " ").split(" ", 2)[1].trim();

                request = new Request(message);
                outToServer.writeObject(request);
                outToServer.flush();


                if (command.equals("execute_script")) {
                    FileSender.sendScriptFile(arg, outToServer);
                }

                if (message.isEmpty()) continue;
                response = (Response) inFromServer.readObject();
                if (response == null) continue;
                else if (response.getExitStatus()) {
                    System.out.print(response.getMessage());
                    System.exit(333);
                }
                if (command.equals("show") || command.equals("filter_starts_with_name")) {
                    printCollection(response);
                }
                else {
                    musicBandsCollection = response.getMusicBandsCollection();
                    System.out.println(response.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static void printCollection(Response response) {
        musicBandsCollection = response.getMusicBandsCollection();
        if (musicBandsCollection != null && !musicBandsCollection.isEmpty()) {
            System.out.printf("|%-30s | %-30s | %-20s|%n", "Название группы", "Лучший альбом", "Количество продаж");
            System.out.println("_".repeat(88));
            musicBandsCollection
                    .stream()
                    .forEach(band -> System.out.printf(
                            "|%-30s | %-30s | %-20.0f|%n",
                            band.getName(),
                            band.getBestAlbum().getName(),
                            band.getBestAlbum().getSales())
                    );

        } else {
            System.out.println(response.getMessage());
        }
    }

}
