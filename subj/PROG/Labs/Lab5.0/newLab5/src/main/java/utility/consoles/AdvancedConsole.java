package utility.consoles;

import commands.History;
import managers.CollectionManager;
<<<<<<< HEAD
import managers.CommandManager;
=======
>>>>>>> e4d6471d86b93e4c7c204de028a3aff45310167a
import utility.ExecutionResponse;
import utility.Invoker;
import utility.interfaces.Console;

import java.util.NoSuchElementException;

public class AdvancedConsole extends StandartConsole implements Console {
    private final String PROMPT = "$ ";
<<<<<<< HEAD
    protected Invoker invoker;
    private final CommandManager commandManager = new CommandManager(this, collectionManager);

=======
>>>>>>> e4d6471d86b93e4c7c204de028a3aff45310167a
    public AdvancedConsole(CollectionManager collectionManager, Invoker invoker) {
        super(collectionManager, invoker);
    }

    @Override
<<<<<<< HEAD
    public void setInvoker(Invoker invoker) {
        this.invoker = invoker;
    }
    @Override
    public void launch() {
//        collectionManager.chooseTypeOfCollection();
=======
    public void printPrompt(){
        System.out.print(PROMPT);
    }


    @Override
    public void launch() {
        collectionManager.chooseTypeOfCollection();
>>>>>>> e4d6471d86b93e4c7c204de028a3aff45310167a
        try {
            ExecutionResponse commandStatus;
            String[] userCommand = {"", ""};
            while (true) {
                printPrompt();
                userCommand = (nextLine() + " ").split(" ", 2);
                userCommand[0] = userCommand[0].toLowerCase().trim();
                userCommand[1] = userCommand[1].toLowerCase().trim();
                if (userCommand[0].equals("exit")) break;
<<<<<<< HEAD
                if (userCommand[0].isEmpty()) continue;

                if (userCommand[0].equals("up")) {
                    String previousCommand = commandManager.getPreviousCommand();
                    if (previousCommand != null) {
                        println(previousCommand);
                    }
                    continue;
                }
                if (userCommand[0].equals("dn")) {
                    String nextCommand = commandManager.getNextCommand();
                    if (nextCommand != null) {
                        println(nextCommand);
                    }
                    continue;
                }
                commandStatus = invoker.execute(userCommand);

=======
                if (userCommand[0].equals("[a")) {
                    println("up");
                }
                if (userCommand[0].isEmpty()) continue;
                commandStatus = invoker.execute(userCommand);
>>>>>>> e4d6471d86b93e4c7c204de028a3aff45310167a
                println(commandStatus.getMessage());
            }
        } catch (NoSuchElementException e) {
        }
    }

<<<<<<< HEAD
    @Override
    public void printPrompt() {
        System.out.print(PROMPT);
    }

=======
>>>>>>> e4d6471d86b93e4c7c204de028a3aff45310167a
}
