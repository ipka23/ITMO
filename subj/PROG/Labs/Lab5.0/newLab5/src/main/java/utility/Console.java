package utility;

import java.util.Scanner;

public interface Console {
    void print(Object o);
    void println(Object o);
    void selectFileScanner(Scanner scanner);
    String nextLine();
    boolean hasNextLine();
    void printPrompt();
    String getScriptPrompt();
    void printError(String error);
    void selectConsoleScanner();
}
