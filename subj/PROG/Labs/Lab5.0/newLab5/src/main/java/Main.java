import commands.*;
import managers.CollectionManager;
import managers.CommandManager;
import managers.FileManager;
import utility.Console;
import utility.Runner;
import utility.StandartConsole;



public class Main {

    public static void main(String[] args) {
        Console console = new StandartConsole();
        if (args.length == 0 || args[0].isEmpty()) {
            console.print("Введите имя файла как аргумент командой строки!");
            System.exit(0);
        }
        FileManager fileManager = new FileManager(args[0].trim(), console);

        CollectionManager collectionManager = new CollectionManager(fileManager, console);
        CommandManager commandManager = new CommandManager();
        Runner runner = new Runner(console, commandManager, collectionManager);
        commandManager.add("add", new Add(console, collectionManager));
        commandManager.add("help", new Help(console, commandManager));
        commandManager.add("info", new Info(console, collectionManager));
        commandManager.add("clear", new Clear(console, collectionManager));
        commandManager.add("save", new Save(console, collectionManager));
        commandManager.add("show", new Show(console, collectionManager));
        commandManager.add("remove_by_id", new RemoveByID(console, collectionManager));
        commandManager.add("update", new Update(console, collectionManager));
        commandManager.add("add_if_max", new AddIfMax(console, collectionManager));
        commandManager.add("add_if_min", new AddIfMin(console, collectionManager));
        commandManager.add("remove_greater", new RemoveGreater(console, collectionManager));
        commandManager.add("max_by_best_album", new MaxByBestBestAlbum(console, collectionManager));
        commandManager.add("filter_starts_with_name", new FilterStartsWithName(console, collectionManager));
        commandManager.add("print_field_ascending_establishment_date", new PrintFieldAscendingEstablishmentDate(console, collectionManager));
        commandManager.add("execute_script", new ExecuteScript(console, collectionManager, commandManager, runner));
        commandManager.add("exit", new Exit(console));

        runner.interactiveMode();
    }
}