import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class Cient {
    public static void main(String[] args) throws IOException {
        Socket client = new Socket("ipka23", 23);

        InputStream inFromServer = client.getInputStream();
        OutputStream outToServer = client.getOutputStream();

        DataInputStream in = new DataInputStream(inFromServer);
        inFromServer.readAllBytes();
        client.close();
        System.out.println(in);
    }
}
