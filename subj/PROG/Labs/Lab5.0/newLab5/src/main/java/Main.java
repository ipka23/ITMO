import managers.CollectionManager;
import managers.CommandManager;
import managers.FileManager;
import utility.*;
import utility.consoles.AdvancedConsole;
import utility.consoles.AlmostUnixConsole;
import utility.consoles.StandartConsole;
import utility.consoles.UnixConsole;
import utility.interfaces.Console;


public class Main {

    public static void main(String[] args) {

//        Console console = new StandartConsole(null, null);
//        Console console = new UnixConsole(null, null);
//        Console console = new AlmostUnixConsole(null, null, null);
        Console console = new AdvancedConsole(null, null);
        if (args.length == 0) {
            console.print("Введите имя файла как аргумент командой строки!");
            System.exit(2);
        }

        FileManager fileManager = new FileManager(args[0].trim(), console);
        CollectionManager collectionManager = new CollectionManager(fileManager, console);
        console.setCollectionManager(collectionManager);


        CommandManager commandManager = new CommandManager(console, collectionManager);
        console.setCommandManager(commandManager);

        Invoker invoker = new Invoker(commandManager);
        console.setInvoker(invoker);

        commandManager.setRunner(invoker);

        console.launch();
    }
}