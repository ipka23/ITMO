package utility.consoles;

import managers.CollectionManager;
import managers.CommandManager;
import utility.ExecutionResponse;
import utility.Invoker;
import utility.interfaces.Console;

import java.io.IOException;
import java.util.NoSuchElementException;

public class AdvancedConsole extends StandartConsole implements Console {
    private final String PROMPT = "$ ";
    protected Invoker invoker;
    private final CommandManager commandManager;

    public AdvancedConsole(CollectionManager collectionManager, Invoker invoker) {
        super(collectionManager, invoker);
        this.commandManager = new CommandManager(this, collectionManager);
    }

    @Override
    public void setInvoker(Invoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public void launch() {
        try {
            ExecutionResponse commandStatus;
            String input;
            while (true) {
                printPrompt();






                input = nextLine().trim();
                if (input.equals("exit")) break;
                if (input.isEmpty()) continue;
                if (input.equals("h")) {
                    println(commandManager.getHistory() + "\nsize: " + commandManager.getHistorySize() + "\nindex: " + commandManager.getHistoryIndex());
                    continue;
                }
                if (input.equals("up")) { //\\033[A
                    String previousCommand = commandManager.getPreviousCommand();
                    if (previousCommand != null) {
                        println(previousCommand);
                    }
                    continue;
                }
                if (input.equals("dn")) {
                    String nextCommand = commandManager.getNextCommand();
                    if (nextCommand != null) {
                        println(nextCommand);
                    }
                    continue;
                }

                commandManager.addToHistory(input);
                commandManager.resetHistoryIndex();

                String[] userCommand = input.split(" ", 2);
                commandStatus = invoker.execute(userCommand);
                println(commandStatus.getMessage());
            }
        } catch (NoSuchElementException e) {
        }
    }



    public void arrowProcessing() throws IOException {
        while (true) {
            printPrompt();
            byte firstByte = (byte) System.in.read();
            if (firstByte == 27) {
                byte secondByte = (byte) System.in.read();
                byte thirdByte = (byte) System.in.read();
                if (secondByte == 91 && thirdByte == 65) {
                    println(commandManager.getPreviousCommand());
                }
            }
        }
    }




    @Override
    public void printPrompt() {
        System.out.print(PROMPT);
    }
}