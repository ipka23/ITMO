package utility;

import java.util.Scanner;

/**
 * Интерфейс консоли(ввод команд и вывод результата)
 *
 * @author ipka23
 */
public interface Console {
    void print(Object obj);

    void println(Object obj);

    String readln();

    boolean hasNextLine();

    void printError(Object obj);

    void printPrompt();

    void selectConsoleScanner();

    void selectFileScanner(Scanner scanner);

    String getScriptPrompt();
}