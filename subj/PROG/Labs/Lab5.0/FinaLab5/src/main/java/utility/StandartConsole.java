package utility;

import managers.CollectionManager;
import managers.FileManager;
import utility.interfaces.Console;

import java.util.NoSuchElementException;
import java.util.Scanner;
//Invoker, CollectionManager
public class StandartConsole implements Console {
    private Scanner scanner;// = new Scanner(System.in);
    private final String PROMPT = "$ ";
    private final String SCRIPT_PROMPT = "# ";
    private Invoker invoker;
    private CollectionManager collectionManager;

    public StandartConsole(Invoker invoker, CollectionManager collectionManager) {
        this.invoker = invoker;
        this.collectionManager = collectionManager;
    }

    public StandartConsole() {

    }


    @Override
    public Scanner getScanner() {
        return scanner;
    }

    @Override
    public String nextLine() {
        return scanner.nextLine();
    }

    @Override
    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    @Override
    public void println(Object o) {
        System.out.println(o);
    }

    @Override
    public void print(Object o) {
        System.out.print(o);
    }

    @Override
    public void printPrompt() {
        System.out.print(PROMPT);
    }

    @Override
    public void setInvoker(Invoker invoker) {
        this.invoker = invoker;
    }
    @Override
    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public String getScriptPrompt() {
        return SCRIPT_PROMPT;
    }


    @Override
    public void launch() {
        try {
            collectionManager.chooseTypeOfCollection();
            while (true) {
                printPrompt();
                String input = nextLine();
                if (input.trim().isEmpty()) continue;
                if (input.equals("exit")) break;
                String[] command = (input + " ").split(" ", 2);
                command[0] = command[0].toLowerCase().trim();
                command[1] = command[1].toLowerCase().trim();
                ExecutionResponse commandStatus = invoker.execute(command);
                print(commandStatus.getMessage());
            }
        } catch (NoSuchElementException e) {}
    }

}
