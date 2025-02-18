import commands.*;
import managers.CollectionManager;
import managers.CommandManager;
import managers.DumpManager;
import utility.Console;
import utility.Runner;
import utility.StandartConsole;


public class Main {

    public static void main(String[] args) {
        Console console = new StandartConsole();
        if (args.length == 0) {
            console.println("Введите имя загружаемого файла как аргумент командной строки");
            System.exit(1);
        }

        CommandManager commandManager = new CommandManager();
        DumpManager dumpManager = new DumpManager(args[0], console);
        CollectionManager collectionManager = new CollectionManager(dumpManager);




        commandManager.addCommand(new Add(console, collectionManager));
        commandManager.addCommand(new Help(console, commandManager));
        commandManager.addCommand(new Info(console, collectionManager));
        commandManager.addCommand(new Clear(console, collectionManager));
        commandManager.addCommand(new Save(console, collectionManager));



        Runner runner = new Runner(console, commandManager);
        runner.interactiveMode();
    }


}