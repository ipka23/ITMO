import utility.interfaces.Console;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class StandartConsole implements Console {
    private Scanner scanner = new Scanner(System.in);
    private final String PROMPT = "$ ";
    private Invoker invoker;
    public StandartConsole() {

    }
    @Override
    public void nextLine() {
        scanner.nextLine();
    }

    @Override
    public void hasNextLine() {
        scanner.hasNextLine();
    }

    @Override
    public void println(Object o) {
        System.out.println(o);
    }

    @Override
    public void print(Object o) {
        System.out.print(o);
    }

    @Override
    public void launch() {
        try {
            while (true) {
                String input = scanner.nextLine();
                print(PROMPT);
                if (input.trim().isEmpty()) continue;
                if (input.equals("exit")) break;
                String[] command = (input + " ").split(" ", 2);
                invoker.execute(command);
            }
        } catch (NoSuchElementException e) {

        }
    }

}
