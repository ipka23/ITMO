import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.ExecutionResponse;
import utility.Invoker;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static int PORT = 2223;
    private static ServerSocket serverSocket;
    private static Socket socket;
    private static BufferedReader inFromClient;
    private static BufferedWriter outToClient;
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private static Invoker invoker;

    public Server(Invoker invoker) {
        Server.invoker = invoker;
    }

    public static void main(String[] args) {
        run();
    }


    public static void run() {
        logger.info("Server has started on port: {}", PORT);
        try {
            while (true) {
                serverSocket = new ServerSocket(PORT);
                socket = serverSocket.accept();
                logger.info("Client has connected!");
                break;
            }

            while (true) {
                inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                outToClient = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                String message = inFromClient.readLine();

                String[] command = (message + " ").split(" ", 2);
                command[0] = command[0].toLowerCase().trim();
                command[1] = command[1].toLowerCase().trim();
                ExecutionResponse commandStatus = invoker.execute(command);

                outToClient.write(commandStatus.getMessage());
                outToClient.newLine();
                outToClient.flush();
            }
        } catch (IOException e) {
            System.out.println("Server_Даун1");
        } finally {
            try {
                inFromClient.close();
                outToClient.close();
                socket.close();
                serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
