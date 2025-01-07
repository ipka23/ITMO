import javax.crypto.spec.PSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new FileInputStream("C:/Users/ilyai/OneDrive/Рабочий стол/ITMO/Labs/Prog/Lab5.0/test/inFile.txt");
            out = new FileOutputStream("C:/Users/ilyai/OneDrive/Рабочий стол/ITMO/Labs/Prog/Lab5.0/test/outFile.txt");

            byte[] buffer = new byte[40];
            int bytesRead;

            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                for (int i = 0; i < buffer.length; i++) {
                    System.out.println(buffer[i] +" "+ bytesRead);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing streams: " + e.getMessage());
            }
        }
    }
}
