import common_utility.network.Request;
import common_utility.network.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server_managers.CollectionManager;
import server_managers.CommandManager;
import server_managers.FileManager;
import server_utility.Invoker;
import server_utility.consoles.ClientConsole;
import server_utility.consoles.ServerConsole;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static int PORT = 1123;
    private static ServerSocket serverSocket;
    private static Socket socket;
    private static ObjectInputStream inFromClient;
    private static ObjectOutputStream outToClient;
    private static String collectionFileName;
    private static Invoker invoker;
    private static ClientConsole clientConsole;
    private static FileManager fileManager;
    private static CollectionManager collectionManager;
    private static CommandManager commandManager;
    private static Logger log = LoggerFactory.getLogger("ServerConsole");

    public static void main(String[] args) {

        fileManager = new FileManager(null, clientConsole, null);
        collectionManager = new CollectionManager(fileManager, clientConsole);
        commandManager = new CommandManager();
        invoker = new Invoker(commandManager, clientConsole);

        clientConsole = new ClientConsole(invoker);

        fileManager.setCollectionManager(collectionManager);
        clientConsole.setCollectionManager(collectionManager);


        run();
    }

    public static void run() {
        log.info("Server has started on port: {}", PORT);
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                socket = serverSocket.accept();
                log.info("Client has connected!");
                outToClient = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                outToClient.flush();
                clientConsole.setObjectOutputStream(outToClient);
                inFromClient = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                clientConsole.setObjectInputStream(inFromClient);
                log.info("Successfully declared in & out streams");
                collectionFileName = getFile();
                fileManager.setFile(collectionFileName);
                break;
            }
            commandManager.declareCommands(clientConsole, collectionManager, invoker, inFromClient, outToClient);
            Runnable serverConsole = new ServerConsole(collectionManager);
            new Thread(serverConsole).start();
            clientConsole.launch();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }


    private static String getFile() throws IOException, ClassNotFoundException {
        Request request = (Request) inFromClient.readObject();
        collectionFileName = request.getMessage();
        Response response;
        try {
            if (collectionFileName.isEmpty()) {
                response = new Response(true, "Введите имя файла как аргумент командной строки!");
            } else if (!new File(collectionFileName).exists()) {
                response = new Response(true, "Файл \"" + collectionFileName + "\" не найден!!!!");
            } else if (!new File(collectionFileName).canRead()) {
                response = new Response(true, "Нет прав на чтение файла \"" + collectionFileName + "\"!");
            } else {
                response = new Response(false);
                log.info("File received: {}", collectionFileName);
            }
            outToClient.writeObject(response);
            outToClient.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return collectionFileName;
    }

}
