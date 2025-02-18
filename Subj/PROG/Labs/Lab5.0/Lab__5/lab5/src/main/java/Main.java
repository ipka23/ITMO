import commands.*;
import managers.CollectionManager;
import managers.CommandManager;
import managers.DumpManager;
import models.Ask;
import utility.Console;
import utility.Runner;
import utility.StandartConsole;


public class Main {

    public static void main(String[] args) throws Ask.AskBreak {
        Console console = new StandartConsole();
        if (args.length == 0) {
            console.println("Введите имя загружаемого файла как аргумент командной строки");
            System.exit(1);
        }

        CommandManager commandManager = new CommandManager();
        DumpManager dumpManager = new DumpManager(args[0], console);
        CollectionManager collectionManager = new CollectionManager(dumpManager);
        commandManager.addCommand("add", new Add(console, collectionManager));
        commandManager.addCommand("help", new Help(console, commandManager));
        commandManager.addCommand("info", new Info(console, collectionManager));
        commandManager.addCommand("clear",new Clear(console, collectionManager));
        commandManager.addCommand("save", new Save(console, collectionManager));
        Runner runner = new Runner(console, commandManager);
        runner.interactiveMode();
    }
}