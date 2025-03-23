import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Serv {
    private static ServerSocket serverSocket;
    private static Socket socket;
    private static BufferedReader in;
    private static BufferedWriter out;

    public static void main(String[] args) throws IOException {
        while (true) {
            serverSocket = new ServerSocket(1234);
            socket = serverSocket.accept();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//            getClientMessage(socket);

        }

    }
}
