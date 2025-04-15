import common_utility.network.Request;
import common_utility.network.Response;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
public class Server {

    public static int PORT = 1231;
    private static ServerSocket serverSocket;
    private static Socket socket;
    private static ObjectInputStream inFromClient;
    private static ObjectOutputStream outToClient;
    private static String collectionFileName;
//    private static Logger log = LoggerFactory.getLogger(Server.class);
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
                console.setObjectOutputStream(outToClient);
                inFromClient = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                console.setObjectInputStream(inFromClient);
                log.info("Successfully declared in & out streams");
                collectionFileName = getFile();
                fileManager.setFile(collectionFileName);
                break;
            }
            commandManager.addCommand("add", new Add(console, collectionManager, inFromClient, outToClient));
            commandManager.addCommand("execute_script", new ExecuteScript(console, invoker, inFromClient, outToClient));
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
