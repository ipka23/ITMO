package server_utility.consoles;

import common_utility.exceptions.ExitException;
import common_utility.network.Response;
import server_managers.CollectionManager;
import server_managers.CommandManager;
import server_utility.Invoker;
import server_utility.interfaces.Console;
import server_utility.interfaces.Launchable;
import server_utility.interfaces.Printable;
import server_utility.interfaces.ScriptExecutable;

import java.io.IOException;
import java.util.Scanner;

//Invoker, CollectionManager
public class StandartConsole implements Console, Launchable, Printable, ScriptExecutable {
    protected Scanner scanner;// = new Scanner(System.in);
    private final String PROMPT = ">";
    private final String SCRIPT_PROMPT = "# ";
    protected Invoker invoker;
    protected CollectionManager collectionManager;

    public StandartConsole(Invoker invoker, CommandManager commandManager) {
        this.invoker = invoker;
        this.collectionManager = collectionManager;
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
    public void launch() {
        try {
//            collectionManager.chooseTypeOfCollection();
            while (true) {
                printPrompt();
                String input = nextLine();
                if (input.trim().isEmpty()) continue;
                String[] command = (input + " ").split(" ", 2);
                command[0] = command[0].toLowerCase().trim();
                command[1] = command[1].toLowerCase().trim();
                Response commandStatus = invoker.execute(command);
                print(commandStatus.getMessage());
            }
        } catch (ExitException e) {
            print(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
