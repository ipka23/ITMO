package utility.consoles;

import managers.CollectionManager;
import utility.CommandHistory;
import utility.ExecutionResponse;
import utility.Invoker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AdvancedConsole extends StandartConsole {
    private final String PROMPT = "$ ";
    protected Invoker invoker;
    private CommandHistory commandHistory;
    private StringBuilder info = new StringBuilder();

    public AdvancedConsole(CollectionManager collectionManager, Invoker invoker) {
        super(invoker, collectionManager);
        this.invoker = invoker;
        this.commandHistory = new CommandHistory();
    }

    public AdvancedConsole() {
        this.commandHistory = new CommandHistory();
    }

    @Override
    public void setInvoker(Invoker invoker) {
        this.invoker = invoker;
        if (this.commandHistory == null) {
            this.commandHistory = new CommandHistory();
        }
    }

    @Override
    public void launch() {
        collectionManager.chooseTypeOfCollection();
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
                            if (character == 65) { // A (стрелка вверх)
                                String previousCommand = commandHistory.getPrevious();
                                if (previousCommand != null) {
                                    System.out.print("\r\033[K");
                                    input.setLength(0);
                                    input.append(previousCommand);
                                    System.out.print(PROMPT + previousCommand);
                                }
                            } else if (character == 66) { // B (стрелка вниз)
                                String nextCommand = commandHistory.getNext();
                                if (nextCommand != null) {
                                    System.out.print("\r\033[K");
                                    input.setLength(0);
                                    input.append(nextCommand);
                                    System.out.print(PROMPT + nextCommand);
                                }
                            } else {
                                reader.reset();
                            }
                        } else {
                            reader.reset();
                        }
                    } else {
                        input.append((char) character);
                        info.append((char) character);
                    }
                }
//                println(info.toString());
                String[] command = (input.toString().trim() + " ").split(" ", 2);
                if (command[0].equals("exit")) break;
                if (command[0].isEmpty()) continue;

                commandHistory.push(command[0] + " " + command[1]);

                commandStatus = invoker.execute(command);
                println(commandStatus.getMessage().substring(0, commandStatus.getMessage().length() - 1));
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