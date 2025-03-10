package utility.consoles;

import commands.History;
import managers.CollectionManager;
import managers.CommandManager;
import utility.ExecutionResponse;
import utility.Invoker;
import utility.interfaces.Console;

import java.util.NoSuchElementException;

public class AdvancedConsole extends StandartConsole implements Console {
    private final String PROMPT = "$ ";
    protected Invoker invoker;
    private final CommandManager commandManager = new CommandManager(this, collectionManager);

    public AdvancedConsole(CollectionManager collectionManager, Invoker invoker) {
        super(collectionManager, invoker);
    }

    @Override
    public void setInvoker(Invoker invoker) {
        this.invoker = invoker;
    }
    @Override
    public void launch() {
//        collectionManager.chooseTypeOfCollection();
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

                println(commandStatus.getMessage());
            }
        } catch (NoSuchElementException e) {
        }
    }

    @Override
    public void printPrompt() {
        System.out.print(PROMPT);
    }

}
