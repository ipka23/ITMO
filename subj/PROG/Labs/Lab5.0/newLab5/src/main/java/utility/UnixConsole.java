package utility;

import managers.CommandManager;
import org.jline.keymap.KeyMap;
import org.jline.reader.*;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class UnixConsole extends StandartConsole {
    private final LineReader lineReader;
    private final Terminal terminal;
    private CommandManager COMMAND_MANAGER = new CommandManager(null, null);
    private static class MatchCommands implements Binding{

    }
//    java -jar target\newLab5-1.0-0.jar MusicBands.json
    public UnixConsole() {
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
    public void printPrompt() {
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