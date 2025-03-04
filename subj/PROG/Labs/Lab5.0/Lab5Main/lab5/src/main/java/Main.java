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
            console.println("Введите переменную окружения как аргумент командной строки");
            System.exit(1);
        }

        CommandManager commandManager = new CommandManager();
        DumpManager dumpManager = new DumpManager(System.getenv(args[0].trim()), console);
        CollectionManager collectionManager = new CollectionManager(dumpManager);
        Runner runner = new Runner(console, commandManager);

        commandManager.addCommand("add", new Add(console, collectionManager));
        commandManager.addCommand("help", new Help(console, commandManager));
        commandManager.addCommand("info", new Info(console, collectionManager));
        commandManager.addCommand("clear", new Clear(console, collectionManager));
        commandManager.addCommand("save", new Save(console, collectionManager));
        commandManager.addCommand("show", new Show(console, collectionManager));
        commandManager.addCommand("remove_by_id", new RemoveByID(console, collectionManager));
        commandManager.addCommand("update", new Update(console, collectionManager));
        commandManager.addCommand("add_if_max", new AddIfMax(console, collectionManager));
        commandManager.addCommand("add_if_min", new AddIfMin(console, collectionManager));
        commandManager.addCommand("remove_greater", new RemoveGreater(console, collectionManager));
        commandManager.addCommand("max_by_best_album", new MaxByBestBestAlbum(console, collectionManager));
        commandManager.addCommand("filter_starts_with_name", new FilterStartsWithName(console, collectionManager));
        commandManager.addCommand("print_field_ascending_establishment_date", new PrintFieldAscendingEstablishmentDate(console, collectionManager));
        commandManager.addCommand("execute_script", new ExecuteScript(console, collectionManager, commandManager, runner));
        commandManager.addCommand("exit", new Exit(console));

        runner.interactiveMode();
    }
}