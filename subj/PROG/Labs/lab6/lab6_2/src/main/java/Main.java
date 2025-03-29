import commands.ExecuteScript;
import managers.CollectionManager;
import managers.CommandManager;
import managers.FileManager;
import utility.Invoker;
import utility.consoles.AdvancedConsole;
import utility.consoles.StandartConsole;
import utility.interfaces.Console;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Введите имя файла как аргумент командной строки!");
            System.exit(2);
        } else if (!new File(args[0]).exists()) {
            System.out.print("Файл \"" + args[0] + "\" не найден!");
            System.exit(2);
        } else if (!new File(args[0]).canRead()) {
            System.out.print("Нет прав на чтение файла \"" + args[0] + "\"!");
            System.exit(2);
        }


        Console console = new StandartConsole();
        FileManager fileManager = new FileManager(null, console, args[0]);
        CollectionManager collectionManager = new CollectionManager(fileManager, console);
        CommandManager commandManager = new CommandManager(console, collectionManager);
        Invoker invoker = new Invoker(commandManager, console);

        fileManager.setCollectionManager(collectionManager);
        console.setCollectionManager(collectionManager);
        console.setInvoker(invoker);

        commandManager.addCommand("execute_script", new ExecuteScript(console, invoker));

        console.launch();

    }
}