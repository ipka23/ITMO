import java.io.*;
import java.net.Socket;

public class TestClient {
    private static int PORT = 3232;
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", PORT);
        try {

            System.out.println("App started!");
            while (!socket.isOutputShutdown()) {
                handleRequest(socket);
            }

        } finally {
            socket.close();
        }
    }
    public static void handleRequest(Socket socket) throws IOException {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while (!socket.isOutputShutdown()) {
                if (in.ready()) {
                    System.out.print("$ ");
                    String line = in.readLine();
                    if (line.equals("exit")) break;
                    String messageToServer = line.trim();

                    out.write("Message from client: " + messageToServer);
                    out.newLine();
                    out.flush();
                    String messageFromServer = in.readLine();
                    System.out.println(messageFromServer);
                    break;
                }
            }
        } finally {
            socket.close();
        }
    }

}
