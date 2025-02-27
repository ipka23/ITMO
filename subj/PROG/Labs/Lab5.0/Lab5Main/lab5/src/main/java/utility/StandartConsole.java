package utility;


import java.util.Scanner;

public class StandartConsole implements Console{
    private static final String PROMPT = "$ ";
    private static final String SCRIPT_PROMPT = "> ";
    private static Scanner fileScanner = null;
    private static Scanner consoleScanner = new Scanner(System.in);



    @Override
    public void print(Object obj) {
        System.out.print(obj);
    }

    @Override
    public void println(Object obj) {
        System.out.println(obj);
    }


    @Override
    public String readln() {
        if (fileScanner == null) return consoleScanner.nextLine();
        return fileScanner.nextLine();
    }

    @Override
    public boolean hasNextLine() throws IllegalStateException {
        if (fileScanner == null) return consoleScanner.hasNextLine();
        return fileScanner.hasNextLine();
    }

    @Override
    public String getPrompt() {
        return PROMPT;
    }

    @Override
    public String getScriptPrompt() {
        return SCRIPT_PROMPT;
    }

    @Override
    public void selectConsoleScanner() {
        fileScanner = null;
    }
    @Override
    public void selectFileScanner(Scanner scanner){
        fileScanner = scanner;
    }




    @Override
    public void printError(Object obj) {
        System.err.println("Error: " + obj);
    }


    @Override
    public void printPrompt() {
        System.out.print(PROMPT);
    }
}