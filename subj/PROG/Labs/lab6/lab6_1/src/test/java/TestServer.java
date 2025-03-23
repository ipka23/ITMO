import utility.exceptions.ExitException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer {
    private static int PORT = 3232;
    private static BufferedReader in;
    private static BufferedWriter out;
    private static ServerSocket serverSocket;
    public static void main(String[] args) throws IOException {

        System.out.println("Server started!");
        while (true) {
            serverSocket = new ServerSocket(PORT);
            handleRequest(serverSocket);
        }
    }
    private static void handleRequest(ServerSocket serverSocket) throws IOException {
        Socket socket = serverSocket.accept();
        System.out.println("Client connected!\nPort: " + PORT);

        try {

            while (true) {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                if (in.ready()) {
                System.out.print("$ ");
                String line = in.readLine();
                if (line.equals("exit")) throw new ExitException();
                out.write("Message from server: "  + line);
                out.newLine();
                out.flush();
                }
            }
//            String messageFromClient = in.readLine();
//            System.out.println(messageFromClient);
//            out.write("Your message was sent successfully!");
//            out.flush();
        } catch (ExitException e) {
            System.out.println(e.getMessage());
        } finally {
            socket.close();
        }
    }
}
