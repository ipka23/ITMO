package mainpac;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Serv implements Runnable {
    public static int PORT = 1234;
    private static ServerSocket serverSocket;
    private static Socket socket;
    private static BufferedReader inFromClient;
    private static BufferedWriter outToClient;
    private static final Logger logger = LoggerFactory.getLogger(Serv.class);


    public static void main(String[] args) {
        Serv serv = new Serv();
        serv.run();
    }


    @Override
    public void run() {
        logger.info("Server has started on port: {}", PORT);
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                socket = serverSocket.accept();
                logger.info("Client has connected!");
                break;
            }

            while (true) {
                inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                outToClient = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                String message = inFromClient.readLine();
                System.out.println("Client sent: \"" + message + "\"");
                outToClient.write("Message from client: \"" + message + "\" has been successfully processed!");
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
