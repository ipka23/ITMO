package utility.consoles;

import managers.CollectionManager;
import managers.CommandManager;
import utility.ExecutionResponse;
import utility.Invoker;
import utility.interfaces.Console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            ExecutionResponse commandStatus;
            StringBuilder input = new StringBuilder();
            byte character;

            while (true) {
                printPrompt();
                input.setLength(0);

                while ((character = (byte) reader.read()) != -1) {
                    if (character == '\n') {
                        break;
                    } else if (character == 27) { // ESC
                        if (reader.read() == 91) { // [
                            character = (byte) reader.read();
                            if (character == 65) {
                                String previousCommand = commandManager.getPreviousCommand();
                                if (previousCommand != null) {
                                    print("\r" + PROMPT + previousCommand);
                                    input.setLength(0);
                                    input.append(previousCommand);
                                }
                            } else if (character == 66) { // B
                                String nextCommand = commandManager.getNextCommand();
                                if (nextCommand != null) {
                                    print("\r" + PROMPT + nextCommand);
                                    input.setLength(0);
                                    input.append(nextCommand);
                                }
                            }
                        }
                    } else {
                        input.append((char) character);
                    }
                }

                if (input.toString().trim().equals("exit")) break;
                if (input.toString().trim().isEmpty()) continue;

                String commandString = input.toString().trim();
                commandManager.addToHistory(commandString);
                commandManager.resetHistoryIndex();

                String[] userCommand = commandString.split(" ", 2);
                commandStatus = invoker.execute(userCommand);
                println(commandStatus.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printPrompt() {
        System.out.print(PROMPT);
    }
}