package server.managers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionManager {
//    private static final int PORT = 2323;
//    private static Socket socket;
//    private static ServerSocket serverSocket;
//    private static BufferedReader inFromClient;
//    private static BufferedWriter outToClient;
//    private static final Logger logger = LoggerFactory.getLogger(ConnectionManager.class);
//
//    public static void main(String[] args) {
//        new ConnectionManager().run();
//    }
//
//    public void run() {
//        logger.info("server.Server has started on port: {}", PORT);
//        try {
//            while (true) {
//                serverSocket = new ServerSocket(PORT);
//                socket = serverSocket.accept();
//                logger.info("client.Client has connected!");
//                break;
//            }
//
//            while (true) {
//                inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                outToClient = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//                String message = inFromClient.readLine();
//                System.out.println("client.Client sent: \"" + message + "\"");
//                outToClient.write("Message from client: \"" + message + "\" has been successfully processed!");
//                outToClient.newLine();
//                outToClient.flush();
//            }
//        } catch (IOException e) {
//            System.out.println("Server_Даун1");
//        } finally {
//            try {
//                inFromClient.close();
//                outToClient.close();
//                socket.close();
//                serverSocket.close();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//
}
