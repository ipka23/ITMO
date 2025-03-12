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
            int character;

            while (true) {
                printPrompt();
                input.setLength(0);

                while ((character = reader.read()) != -1) {
                    if (character == '\n') {
                        break;
                    } else if (character == 27) { // ESC
                        reader.mark(2);
                        if (reader.read() == 91) { // [
                            character = reader.read();
                            if (character == 65) { // A
                                String previousCommand = commandManager.getPreviousCommand();
                                if (previousCommand != null) {
                                    System.out.print("\r\033[K");
                                    input.setLength(0);
                                    input.append(previousCommand);
                                    println(PROMPT + previousCommand);
                                }
                            } else if (character == 66) { // B
                                String nextCommand = commandManager.getNextCommand();
                                if (nextCommand != null) {
                                    System.out.print("\r\033[K");
                                    input.setLength(0);
                                    input.append(nextCommand);
                                    println(PROMPT + nextCommand);
                                }
                            } else {
                                reader.reset();
                            }
                        } else {
                            reader.reset();
                        }
                    } else {
                        input.append((char) character);
                        System.out.print((char) character);
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

//    private void clearLine() {
//        System.out.print("\r\033[K"); // ANSI escape sequence to clear the line
//    }

    @Override
    public void printPrompt() {
        System.out.print(PROMPT);
    }
}