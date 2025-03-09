package utility.consoles;

import managers.CollectionManager;
import managers.CommandManager;
import utility.interfaces.Console;
import utility.ExecutionResponse;
import utility.Invoker;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class StandartConsole implements Console {
    private final String PROMPT = ">";
    private final String SCRIPT_PROMPT = "# ";
    private CollectionManager collectionManager;
    private Invoker invoker;
    protected final Scanner consoleScanner = new Scanner(System.in);
    protected Scanner fileScanner;

    public StandartConsole(CollectionManager collectionManager, Invoker invoker) {
        this.collectionManager = collectionManager;
        this.invoker = invoker;
    }

    @Override
    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void setInvoker(Invoker invoker) {
        this.invoker = invoker;
    }


    @Override
    public void setCommandManager(CommandManager commandManager) {

    }

    @Override
    public void print(Object o) {
        System.out.print(o);
    }

    @Override
    public void println(Object o) {
        System.out.println(o);
    }

    @Override
    public void selectFileScanner(Scanner scanner) {
        this.fileScanner = scanner;
    }

    @Override
    public String nextLine() {
        if (fileScanner != null) {
            return fileScanner.nextLine();
        } else {
            return consoleScanner.nextLine();
        }
    }

    @Override
    public boolean hasNextLine() {
        if (fileScanner != null) {
            return fileScanner.hasNextLine();
        } else {
            return consoleScanner.hasNextLine();
        }
    }

    @Override
    public void printPrompt() {
        System.out.print(PROMPT);
    }

    @Override
    public String getScriptPrompt() {
        return SCRIPT_PROMPT;
    }

    @Override
    public void printError(String error) {
        System.err.println("ERROR: " + error);
    }

    @Override
    public void selectConsoleScanner() {
        fileScanner = null;
    }

    @Override
    public void launch() {
        collectionManager.chooseTypeOfCollection();
        try {
            ExecutionResponse commandStatus;
            String[] userCommand = {"", ""};
            while (true) {
                printPrompt();
                userCommand = (nextLine() + " ").split(" ", 2);
                userCommand[0] = userCommand[0].toLowerCase().trim();
                userCommand[1] = userCommand[1].toLowerCase().trim();
                if (userCommand[0].equals("exit")) break;
                if (userCommand[0].isEmpty()) continue;
                commandStatus = invoker.execute(userCommand);
                println(commandStatus.getMessage());
            }
        } catch (NoSuchElementException e) {
        }
    }
}
