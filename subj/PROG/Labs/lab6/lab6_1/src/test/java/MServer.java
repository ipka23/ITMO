import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(23);
        while (true) {
            Socket client = serverSocket.accept();
            handleRequest(client);
        }
    }

    private static void handleRequest(Socket client) throws IOException {
        StringBuilder sb = new StringBuilder();

        BufferedReader clientMessage = new BufferedReader(new InputStreamReader(client.getInputStream()));
        BufferedWriter responseToClient = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        String request = clientMessage.readLine();

        sb.append(request);
        responseToClient.write(sb.toString());
        responseToClient.newLine();
    }
}
