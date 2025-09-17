import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FilePrinter {
    private PrintWriter printWriter = null;

    public FilePrinter(Path path) {
        try {
            printWriter = new PrintWriter(Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND), true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public static void main(String[] args) {
        FilePrinter simpliestLogger = new FilePrinter(Path.of("log.txt"));
        simpliestLogger.getPrintWriter().println("123");
    }


}
