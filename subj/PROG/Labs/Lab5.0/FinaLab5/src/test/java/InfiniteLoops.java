import java.util.NoSuchElementException;
import java.util.Scanner;

public class InfiniteLoops {
    public static void main(String[] args) {
        String line;
        try (Scanner scanner = new Scanner(System.in)){
            System.out.println("=== while (true) ===");
            while (true) {
                System.out.print("# ");
                line = scanner.nextLine();
                System.out.println("Вы ввели: " + line);
                if (line.trim().equals("exit")) break;
            }
            System.out.println("=== for (;;) ===");
            for (;;) {
                System.out.print("# ");
                line = scanner.nextLine();
                System.out.println("Вы ввели: " + line);
                if (line.trim().equals("exit")) break;
            }
            System.out.println("=== for (byte i = 0; i < 128; i++) ===");
            for (byte i = 0; i < 128; i++) {
                System.out.print("# ");
                line = scanner.nextLine();
                System.out.println("Вы ввели: " + line);
                if (line.trim().equals("exit")) break;
            }
        } catch (NoSuchElementException e) {
            System.err.println(e.getMessage());
        }
    }
}
