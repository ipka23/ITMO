package utility.consoles;

import managers.CollectionManager;
import managers.CommandManager;
import org.jline.reader.*;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import utility.ExecutionResponse;
import utility.Invoker;

import java.io.IOException;
import java.util.NoSuchElementException;

public class UnixConsole extends StandartConsole {
    private final String PROMPT = "$ ";
    private final LineReader lineReader;
    private CollectionManager collectionManager;
    private Invoker invoker;
    private final Terminal terminal;
    private CommandManager COMMAND_MANAGER = new CommandManager(null, null);

    public UnixConsole(CollectionManager collectionManager, Invoker invoker) {
        super(collectionManager, invoker);
        try {
            terminal = TerminalBuilder
                    .builder()
                    .system(true)
                    .build();
            Completer completer = new StringsCompleter(COMMAND_MANAGER.getCommandsList());
            lineReader = LineReaderBuilder
                    .builder()
                    .terminal(terminal)
                    .completer(completer)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка инициализации терминала!", e);
        }
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
    public void printPrompt() {
    }

    @Override
    public String getPrompt() {
        return PROMPT;
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

    @Override
    public String nextLine() {
        if (fileScanner != null) {
            return fileScanner.nextLine();
        } else {
            return lineReader.readLine("$ ");
        }
    }
}