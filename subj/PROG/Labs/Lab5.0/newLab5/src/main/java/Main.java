import managers.CollectionManager;
import managers.CommandManager;
import managers.FileManager;
import utility.Console;
import utility.Runner;
import utility.UnixConsole;


public class Main {

    public static void main(String[] args) {
        Console console = new UnixConsole();
        if (args.length == 0 || args[0].isEmpty()) {

            console.print("Введите имя файла как аргумент командой строки!");

            System.exit(0);
        }

        FileManager fileManager = new FileManager(args[0].trim(), console);

        CollectionManager collectionManager = new CollectionManager(fileManager, console);

        CommandManager commandManager = new CommandManager(console, collectionManager);

        Runner runner = new Runner(console, commandManager, collectionManager);

        commandManager.setRunner(runner);

        runner.interactiveMode();
    }
}