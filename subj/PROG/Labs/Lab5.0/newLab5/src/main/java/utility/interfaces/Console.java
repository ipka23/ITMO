package utility.interfaces;

import managers.CollectionManager;
import managers.CommandManager;
import utility.Invoker;

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
    void launch();
    void setCollectionManager(CollectionManager collectionManager);
    void setInvoker(Invoker invoker);
    void setCommandManager(CommandManager commandManager);
}