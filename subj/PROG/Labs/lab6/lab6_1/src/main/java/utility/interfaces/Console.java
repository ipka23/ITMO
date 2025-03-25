package utility.interfaces;

import managers.CollectionManager;
import managers.CommandManager;
import utility.Invoker;

import java.util.Scanner;


public interface Console {
    String nextLine();
    void launch();
    void println(Object o);
    void print(Object o);
    void printPrompt();
    void setInvoker(Invoker invoker);
    void setCollectionManager(CollectionManager collectionManager);
    void setCommandManager(CommandManager commandManager);
    void setScanner(Scanner scanner);
    String getScriptPrompt();
}