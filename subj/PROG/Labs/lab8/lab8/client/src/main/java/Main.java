import network.Client;

import java.io.IOException;
import java.net.Socket;

public class Main {
    public static int port = 1232;
    private static String hostName = "localhost";
    private static Socket socket;

    public static void main(String[] args) throws IOException {

        socket = new Socket(hostName, port);
        Client client = new Client(port, hostName, socket);
        try {
            Runner.run(client);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            socket.close();
        }
    }
}
