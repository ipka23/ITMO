import java.util.IllegalFormatException;
import java.util.Scanner;

public class StandartConsole {
    private static final String P = "$ ";
    private static Scanner defScanner = new Scanner(System.in);

    public void prompt(){
        System.out.print(P);
    }

    public String read() throws IllegalArgumentException, IllegalFormatException {
        return defScanner.nextLine();
    }
}
