package network;

import common_entities.MusicBand;
import common_utility.database.User;
import common_utility.network.Request;
import common_utility.network.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;

//TODO сделать обработку авторизации/регистрации
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
            while (true) {
                Response prompt = getResponse(inFromServer);
                if (prompt != null) {
                    System.out.print(prompt.getMessage());
                } else {
                    Response response1 = getResponse(inFromServer);
                    System.out.print(response1.getMessage());
                }

                String message = userInput.nextLine().trim();

                command = (message + " ").split(" ", 2)[0].trim().toLowerCase();
                arg = (message + " ").split(" ", 2)[1].trim();

                request = new Request(message);
                sendRequest(request, outToServer);


                if (command.equals("execute_script")) {
                    FileSender.sendScriptFile(arg, outToServer);
                }

                if (message.isEmpty()) continue;
                response = getResponse(inFromServer);
                if (response == null) {
                    continue;
                } else if (response.getExitStatus()) {
                    System.out.print(response.getMessage());
                    System.exit(333);
                }
                if (command.equals("show") || command.equals("filter_starts_with_name")) {
                    printCollection(response);
                } else {
                    musicBandsCollection = response.getMusicBandsCollection();
                    System.out.println(response.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //todo вынести в отдельный класс, добавить exit
    private static void authentication(ObjectOutputStream outToServer, ObjectInputStream inFromServer, Scanner scanner) {
        String username;
        String password;
        boolean loggedIn;
        try {
            while (true) {
                System.out.print("===========================\n::   Добро пожаловать!   ::\n===========================\nДля входа/регистрации введите - (1/2)\n~ ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) continue;
                if (input.equals("1")) loggedIn = true;
                else if (input.equals("2")) loggedIn = false;
                else continue;
                break;
            }
            while (true) {
                System.out.print("Введите имя пользователя\n~ ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) continue;
                username = input;
                break;
            }
            while (true) {
                if (loggedIn) {
                    System.out.print("Введите пароль\n~ ");
                    String input = scanner.nextLine().trim();
                    if (input.isEmpty()) continue;
                    password = input;
                    sendRequest(new Request("login", new User(username, password)), outToServer);
                    Response response = getResponse(inFromServer);
                    if (!response.getExitStatus()) {
                        System.out.print("Неверный пароль!\n"); // todo fix повторный ввод правильного пароля неверный, сделать обработку ситуаций когда пользователя нет в системе и выдавать соответствующую ошибку

                        continue;
                    } else {
                        System.out.println("Вход успешно выполнен!");
                    }
                    break;
                } else {
                    System.out.print("Придумайте пароль\n~ ");
                    String firstInput = scanner.nextLine().trim();
                    System.out.print("Повторите пароль\n~ ");
                    String secondInput = scanner.nextLine().trim();
                    if (!firstInput.equals(secondInput)) {
                        System.out.print("Пароли не совпадают;\nВведите пароль снова!\n");
                        continue;
                    }
                    password = secondInput;
                    sendRequest(new Request("register", new User(username, password)), outToServer);
                    Response response = getResponse(inFromServer);
                    if (!response.getExitStatus()) {
                        String message = response.getMessage();
                        System.out.print("Ошибка регистрации: " + message);
                        continue; // todo fix
                    } else {
                        System.out.println("Регистрация успешно выполнена!");
                    }
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
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

    private static void sendRequest(Object o, ObjectOutputStream outToServer) throws IOException {
        outToServer.writeObject(o);
        outToServer.flush();
    }

    private static Response getResponse(ObjectInputStream inFromServer) throws IOException, ClassNotFoundException {
        return (Response) inFromServer.readObject();
    }

}
