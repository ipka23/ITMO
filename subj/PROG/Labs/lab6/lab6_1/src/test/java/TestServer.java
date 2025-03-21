import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer {
    private static int PORT = 3232;
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server started!");
        while (!serverSocket.isClosed()) {
            handleRequest(serverSocket);
        }
    }
    private static void handleRequest(ServerSocket serverSocket) throws IOException {
        while (!serverSocket.isClosed()) {
            Socket socket = serverSocket.accept();

            System.out.println("Client connected!\nPort: " + PORT);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.print("$ ");
        }
    }
}
