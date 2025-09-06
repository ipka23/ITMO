package network;

import common_entities.MusicBand;
import common_utility.database.User;
import common_utility.exceptions.ExitClientException;
import common_utility.network.Request;
import common_utility.network.Response;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;
import java.util.stream.Stream;


public class RequestSender {
    private static Collection<MusicBand> musicBandsCollection = new HashSet<>();

    public static void sendMessage(ObjectOutputStream outToServer, ObjectInputStream inFromServer, Scanner userInput) {
        Response response;
        Request request;
        String command;
        String arg;
        try {
            // авторизация
            authentication(outToServer, inFromServer, userInput);
            // основная программа
            outerLoop:
            while (true) {
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
                } else if (response.getExitStatus()/* && !response.getMessage().equals("Отмена ввода...")*/) {
                    System.out.print(response.getMessage());
                    System.exit(1);
                } else {
//                    musicBandsCollection = response.getMusicBandsCollection();
                    System.out.println(response.getMessage());
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void authentication(ObjectOutputStream outToServer, ObjectInputStream inFromServer, Scanner scanner) {
        String username;
        String password;
        boolean loggedIn;
        try {
            while (true) {
                System.out.print("=====================\n: Добро пожаловать! :\n=====================\nДля входа/регистрации введите - (1/2)\n~ ");
                String input = scanner.nextLine().trim();
                if (input.equals("exit")) throw new ExitClientException();
                else if (input.isEmpty()) continue;
                else if (input.equals("1")) loggedIn = true;
                else if (input.equals("2")) loggedIn = false;
                else continue;
                break;
            }
            outerLoop:
            while (true) {
                System.out.print("Введите имя пользователя\n~ ");
                String input = scanner.nextLine().trim();
                if (input.equals("exit")) throw new ExitClientException();
                else if (input.isEmpty()) continue;
                username = input;
                while (true) {
                    if (loggedIn) {
                        System.out.print("Введите пароль\n~ ");
                        String input1 = scanner.nextLine().trim();
                        if (input1.equals("exit")) throw new ExitClientException();
                        if (input1.isEmpty()) continue;
                        password = input1;
                        sendRequest(new Request("login", new User(username, password)), outToServer);
                        Response response = getResponse(inFromServer);
                        if (!response.getExitStatus()) {
                            System.out.println(response.getMessage());
                            continue outerLoop;
                        } else {
                            System.out.println(response.getMessage());
                            break outerLoop;
                        }
                    } else {
                        System.out.print("Придумайте пароль\n~ ");
                        String firstInput = scanner.nextLine().trim();
                        if (firstInput.equals("exit")) throw new ExitClientException();
                        if (firstInput.isEmpty()) continue;
                        System.out.print("Повторите пароль\n~ ");
                        String secondInput = scanner.nextLine().trim();
                        if (secondInput.equals("exit")) throw new ExitClientException();
                        if (secondInput.isEmpty()) continue;
                        if (!firstInput.equals(secondInput)) {
                            System.out.print("Пароли не совпадают;\nВведите пароль снова!\n");
                            continue;
                        }
                        password = secondInput;
                        sendRequest(new Request("register", new User(username, password)), outToServer);
                        Response response = getResponse(inFromServer);
                        if (!response.getExitStatus()) {
                            System.out.println(response.getMessage());
                            continue outerLoop;
                        } else {
                            System.out.println(response.getMessage());
                            break outerLoop;
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void printCollection(Response response) {
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

    private static void sendRequest(Request r, ObjectOutputStream outToServer) throws IOException {
        outToServer.writeObject(r);
        outToServer.flush();
    }

    private static Response getResponse(ObjectInputStream inFromServer) throws IOException, ClassNotFoundException {
        return (Response) inFromServer.readObject();
    }

}
