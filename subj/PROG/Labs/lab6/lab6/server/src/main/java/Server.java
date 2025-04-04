import common_utility.network.Request;
import common_utility.network.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server_commands.Add;
import server_commands.ExecuteScript;
import server_managers.CollectionManager;
import server_managers.CommandManager;
import server_managers.FileManager;
import server_utility.Invoker;
import server_utility.consoles.ClientConsole;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static int PORT = 1123;
    private static ServerSocket serverSocket = null;
    private static Socket socket = null;
//    private static Scanner scanner = new Scanner(System.in);
    private static ObjectInputStream inFromClient = null;
    private static ObjectOutputStream outToClient = null;
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private static String collectionFileName;

    private static Invoker invoker;
    private static ClientConsole console;
    private static FileManager fileManager;
    private static CollectionManager collectionManager;
    private static CommandManager commandManager;

    public static void main(String[] args) {

        fileManager = new FileManager(null, console, null);
        collectionManager = new CollectionManager(fileManager, console);
        commandManager = new CommandManager(console, collectionManager);
        invoker = new Invoker(commandManager, console);

        console = new ClientConsole(invoker);

        fileManager.setCollectionManager(collectionManager);
        console.setCollectionManager(collectionManager);

        commandManager.addCommand("execute_script", new ExecuteScript(console, invoker));


        run();
    }
    public static void run() {
        logger.info("Server has started on port: {}", PORT);
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                socket = serverSocket.accept();
                logger.info("Client has connected!");
                outToClient = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                outToClient.flush();
                console.setObjectOutputStream(outToClient);
                inFromClient = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                console.setObjectInputStream(inFromClient);
                logger.info("Successfully declared in & out streams");
                collectionFileName = getFile();
                fileManager.setFile(collectionFileName);
                break;
            }
            commandManager.addCommand("add", new Add(console, collectionManager, inFromClient, outToClient));
            console.launch();

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
                logger.info("File received: {}", collectionFileName);
            }
            outToClient.writeObject(response);
            outToClient.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return collectionFileName;
    }

}
