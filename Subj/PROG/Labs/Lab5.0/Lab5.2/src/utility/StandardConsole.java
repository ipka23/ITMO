package utility;

import java.util.Scanner;

public class StandardConsole implements Console{
    private static final String PROMPT = "$ ";
    private static Scanner fileScanner;
    private static final Scanner consoleScanner = new Scanner(System.in);



    @Override
    public void print(Object obj) {
        System.out.print(obj);
    }

    @Override
    public void println(Object obj) {
        System.out.println(obj);
    }

    //throws NoSuchElementException, IllegalStateException
    @Override
    public String readln() {
        if (fileScanner != null) return fileScanner.nextLine();
        else return consoleScanner.nextLine();
    }


    //throws IllegalStateException
    @Override
    public boolean isCanReadln() {
        if (fileScanner != null) return fileScanner.hasNextLine();
        else return consoleScanner.hasNextLine();
    }

    @Override
    public void printError(Object obj) {
        System.err.println("Error: " + obj);
    }

//    @Override
//    public void printTable(Object obj1, Object obj2) {
//
//    }

    @Override
    public void printPrompt() {
        System.out.println(PROMPT);
    }

    @Override
    public String getPrompt() {
        return PROMPT;
    }

    // метод для выбора файла как источника ввода сканера вместо консоли
    @Override
    public void selectFileScanner(Scanner scanner) {
        fileScanner = scanner;
    }
    // метод для выбора консоли как источника ввода сканера вместо файла
    @Override
    public void selectConsoleScanner() {
        fileScanner = null;
    }
}
