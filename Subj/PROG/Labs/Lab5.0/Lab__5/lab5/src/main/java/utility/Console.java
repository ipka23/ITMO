package utility;
import java.util.Scanner;

public interface Console {
    void print(Object obj);
    void println(Object obj);
    String readln();
//    boolean isCanReadln();
    void printError(Object obj);
    //    void printTable(Object obj1, Object obj2);
    void printPrompt();
//    String getPrompt();
//    void selectFileScanner(Scanner scanner);
//    void selectConsoleScanner();
}