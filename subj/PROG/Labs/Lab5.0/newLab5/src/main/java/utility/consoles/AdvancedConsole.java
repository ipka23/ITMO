package utility.consoles;

import managers.CollectionManager;
import utility.ExecutionResponse;
import utility.Invoker;
import utility.interfaces.Console;

import java.util.NoSuchElementException;

public class AdvancedConsole extends StandartConsole implements Console {
    private final String PROMPT = "$ ";
    public AdvancedConsole(CollectionManager collectionManager, Invoker invoker) {
        super(collectionManager, invoker);
    }

    @Override
    public void printPrompt(){
        System.out.print(PROMPT);
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
                if (userCommand[0].equals("[a")) {
                    println("up");
                }
                if (userCommand[0].isEmpty()) continue;
                commandStatus = invoker.execute(userCommand);
                println(commandStatus.getMessage());
            }
        } catch (NoSuchElementException e) {
        }
    }

}
