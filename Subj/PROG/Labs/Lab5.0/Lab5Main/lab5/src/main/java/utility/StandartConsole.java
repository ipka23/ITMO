package utility;

import java.util.Scanner;

public class StandartConsole implements Console{
    private static final String PROMPT = "$ ";
    private static final Scanner consoleScanner = new Scanner(System.in);



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
        return consoleScanner.nextLine();
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