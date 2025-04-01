package server_utility.consoles;

import common_utility.network.ExecutionResponse;
import common_utility.exceptions.ExitException;
import server_managers.CollectionManager;
import server_managers.CommandManager;
import server_utility.Invoker;
import server_utility.interfaces.Console;

import java.util.Scanner;

//Invoker, CollectionManager
public class StandartConsole implements Console {
    protected Scanner scanner;// = new Scanner(System.in);
    private final String PROMPT = ">";
    private final String SCRIPT_PROMPT = "# ";
    protected Invoker invoker;
    protected CollectionManager collectionManager;
    protected CommandManager commandManager;

    public StandartConsole(Invoker invoker, CollectionManager collectionManager, CommandManager commandManager) {
        this.invoker = invoker;
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
        setScanner(new Scanner(System.in));
    }

    public StandartConsole() {
        setScanner(new Scanner(System.in));
    }


    @Override
    public String nextLine() {
        return scanner.nextLine();
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
    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public void launch() {
        try {
            collectionManager.chooseTypeOfCollection();
            while (true) {
                printPrompt();
                String input = nextLine();
                if (input.trim().isEmpty()) continue;
                String[] command = (input + " ").split(" ", 2);
                command[0] = command[0].toLowerCase().trim();
                command[1] = command[1].toLowerCase().trim();
                ExecutionResponse commandStatus = invoker.execute(command);
                print(commandStatus.getMessage());
            }
        } catch (ExitException e) {
            print(e.getMessage());
        }
    }

}
