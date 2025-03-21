import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestReader {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String name = "";
        while (true) {
            String line = br.readLine();
            if (line.equals("exit")) break;
            System.out.print("enter your name: ");
            name = line;
        }
        System.out.println("Your name is: " + name);
    }
}
