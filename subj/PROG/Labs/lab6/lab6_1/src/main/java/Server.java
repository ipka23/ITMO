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

        BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        String request = br.readLine();

        sb.append(request);
        bw.write(sb.toString());
        bw.newLine();
    }
}
