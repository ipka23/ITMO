package utility;

import java.util.Scanner;

public interface Console {
    void print(Object obj);
    void println(Object obj);
    String readln();
    boolean hasNextLine();
    void printError(Object obj);
    void printPrompt();
    void selectConsoleScanner();
    void selectFileScanner(Scanner scanner);
    String getPrompt();
    String getScriptPrompt();
}