package utility;

import java.util.Scanner;

public class StandartConsole implements Console {
    private final String PROMPT = ">";
    private final String SCRIPT_PROMPT = "# ";
    protected final Scanner consoleScanner = new Scanner(System.in);
    protected Scanner fileScanner;


    @Override
    public void print(Object o) {
        System.out.print(o);
    }

    @Override
    public void println(Object o) {
        System.out.println(o);
    }

    @Override
    public void selectFileScanner(Scanner scanner) {
        this.fileScanner = scanner;
    }

    @Override
    public String nextLine() {
        if (fileScanner != null) {
            return fileScanner.nextLine();
        } else {
            return consoleScanner.nextLine();
        }
    }

    @Override
    public boolean hasNextLine() {
        if (fileScanner != null) {
            return fileScanner.hasNextLine();
        } else {
            return consoleScanner.hasNextLine();
        }
    }

    @Override
    public void printPrompt() {
        System.out.print(PROMPT);
    }

    @Override
    public String getScriptPrompt() {
        return SCRIPT_PROMPT;
    }

    @Override
    public void printError(String error) {
        System.err.println("ERROR: " + error);
    }

    @Override
    public void selectConsoleScanner() {
        fileScanner = null;
    }
}
