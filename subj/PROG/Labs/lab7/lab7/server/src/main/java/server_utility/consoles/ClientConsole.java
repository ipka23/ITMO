package server_utility.consoles;

import common_utility.exceptions.ExitException;
import common_utility.network.Request;
import common_utility.network.Response;
import lombok.Getter;
import lombok.Setter;
import server_managers.CollectionManager;
import server_managers.CommandManager;
import server_managers.UserManager;
import server_utility.Invoker;
import server_utility.database.User;
import server_utility.interfaces.ObjectStreamsWorkable;

import java.io.*;
import java.sql.SQLException;
import java.util.Scanner;

//Invoker, CollectionManager
public class ClientConsole extends StandartConsole implements ObjectStreamsWorkable {
    protected Scanner scanner;// = new Scanner(System.in);
    private final String PROMPT = ">";
    private final String SCRIPT_PROMPT = "# ";
    protected Invoker invoker;
    @Getter
    protected CollectionManager collectionManager;
    private static ObjectInputStream inFromClient;
    private static ObjectOutputStream outToClient;
    @Setter @Getter
    private boolean scriptMode = false;
    @Getter
    private StringBuilder tmp;
    @Setter @Getter
    private File scriptFile;
    @Getter @Setter
    private UserManager userManager;

    public ClientConsole(Invoker invoker, CollectionManager collectionManager, ObjectInputStream inFromClient, ObjectOutputStream outToClient) {
        this.invoker = invoker;
        this.collectionManager = collectionManager;
        ClientConsole.inFromClient = inFromClient;
        ClientConsole.outToClient = outToClient;

        setScanner(new Scanner(System.in));
    }

    public ClientConsole(Invoker invoker) {
        this.invoker = invoker;
    }


    @Override
    public void setObjectInputStream(ObjectInputStream in) {
        ClientConsole.inFromClient = in;
    }

    @Override
    public void setObjectOutputStream(ObjectOutputStream out) {
        ClientConsole.outToClient = out;
    }


    public String getClientMessage() {
        Request request;
        try {
            request = (Request) inFromClient.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return request.getMessage();
    }


    public void send(Object o) {
        try {
            outToClient.writeObject(o);
            outToClient.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public Request getRequest() throws IOException, ClassNotFoundException {
        if (!scriptMode) {
            return (Request) inFromClient.readObject();
        } else {
            String currentLine;
            try (Scanner fileScanner = new Scanner(new FileReader(scriptFile))) {
                currentLine = fileScanner.nextLine();
            }
            return new Request(currentLine);
        }
    }

    public void sendPrompt() {
        try {
            outToClient.writeObject(new Response(false, PROMPT));
            outToClient.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void launch() {;
        String username;
        String password;
        try {
            while (true) {
                send(new Response(false, "===========================\n||   Добро пожаловать!   ||\n===========================\nДля авторизации введите - 1\nДля регистрации введите - 2\n~ "));
                Request request = (Request) inFromClient.readObject();
                if (request.getMessage().isEmpty()) continue;
                if (request.getMessage().trim().equals("1")){
                    send(new Response(false, "Введите имя пользователя: "));
                    username = ((Request) inFromClient.readObject()).getMessage().trim();
                    send(new Response(false, "Введите пароль: "));
                    password = ((Request) inFromClient.readObject()).getMessage().trim();
                    while (!collectionManager.getDatabaseManager().validatePassword(username, password)){
                        send(new Response(false, "Неверный пароль!\nВведите еще раз: "));
                        password = ((Request) inFromClient.readObject()).getMessage().trim();
                    }
                    userManager.authenticateUser(new User(username, password));
                    break;
                }
                else if (request.getMessage().trim().equals("2")){
                    send(new Response(false, "Введите имя пользователя: "));
                    username = ((Request) inFromClient.readObject()).getMessage().trim();
                    send(new Response(false, "Придумайте пароль: "));
                    String firstInput = ((Request) inFromClient.readObject()).getMessage().trim();
                    send(new Response(false, "Повторите пароль: "));
                    String secondInput = ((Request) inFromClient.readObject()).getMessage().trim();
                    while (!firstInput.equals(secondInput)){
                        send(new Response(false, "Пароли не совпадают!\nПридумайте пароль снова: "));
                        firstInput = ((Request) inFromClient.readObject()).getMessage().trim();
                        send(new Response(false, "Повторите пароль: "));
                        secondInput = ((Request) inFromClient.readObject()).getMessage().trim();
                    }
                    password = secondInput;
                    userManager.registerUser(new User(username, password));
                }
            }
            while (true) {
                sendPrompt();
                Request request = (Request) inFromClient.readObject();
                String command = (request.getMessage() + " ").split(" ", 2)[0];
                String arg =(request.getMessage() + " ").split(" ", 2)[1];;
                if (command.isEmpty()) continue;
                Response response = invoker.execute(new String[]{command, arg});

                send(response);
            }
        } catch (ExitException e) {
            print(e);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //TODO
    // Организовать возможность регистрации и авторизации пользователей.
    // У пользователя есть возможность указать пароль.
    // То есть при запуске консоли сначала выводится
    // Для регистрации/входа введите команду - "reg"/"auth"
}