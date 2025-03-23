import utility.exceptions.ExitException;

import java.io.*;
import java.net.Socket;

public class TestClient {
    private static int PORT = 3232;
    private static BufferedReader in;
    private static BufferedWriter out;
    private static Socket socket;

    public static void main(String[] args) throws IOException {
        try {
            System.out.println("App started!");
            while (true) {
                socket = new Socket("localhost", PORT);
                handleRequest(socket);
            }
        } finally {
            socket.close();
        }
    }

    public static void handleRequest(Socket socket) throws IOException {
        try {
            while (true) {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                System.out.print("$ ");
                String line = in.readLine();
                if (in.ready()) {
                    if (line.equals("exit")) throw new ExitException();
                    out.write("Message from client: " + line);
                    out.flush();
                    System.out.println(line);
                }
            }
        } catch (ExitException e) {
            System.out.println(e.getMessage());
        } finally {
            socket.close();
        }
    }

}
