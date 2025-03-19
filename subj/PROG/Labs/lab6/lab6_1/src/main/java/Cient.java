import java.io.*;
import java.net.Socket;

public class Cient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("ipka23", 23);

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        int c;
        while ( (c = in.read()) != -1) {
            System.out.println((char) c);
        }
        socket.close();
    }
}
