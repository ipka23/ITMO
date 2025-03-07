package utility;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class UnixConsole extends StandartConsole {
    private final LineReader lineReader;
    private final Terminal terminal;


    public UnixConsole() {
        try {
            terminal = TerminalBuilder.builder().system(true).build();
            lineReader = LineReaderBuilder.builder().terminal(terminal).build();
        } catch (IOException e) {
            throw new RuntimeException("Error initializing terminal", e);
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