import commands.ExecuteScript;
import managers.CollectionManager;
import managers.CommandManager;
import managers.FileManager;
import utility.Invoker;
import utility.StandartConsole;
import utility.interfaces.Console;

import java.io.File;
import java.nio.file.Paths;
import java.util.Scanner;
// execute_script C:\Users\ilyai\OneDrive\Рабочий стол\ITMO\subj\PROG\Labs\Lab5.0\FinaLab5\script.txt
// execute_script /Users/ipka23/Desktop/ITMO/Subj/PROG/Labs/Lab5.0/FinaLab5/script.txt
public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Введите имя файла как аргумент командной строки!");
            System.exit(2);
        }
        // доделать: Программа должна корректно работать с неправильными данными (ошибки пользовательского ввода, отсутствие прав доступа к файлу и т.п.)
        else if (!new File(args[0]).exists()) {
            System.out.print("Файл \"" + args[0] + "\" не найден!");
            System.exit(2);
        }
        else if (!new File(args[0]).canRead()) {
            System.out.print("Нет прав на чтение файла \"" + args[0] + "\"!");
            System.exit(2);
        }

        Console console = new StandartConsole();

        FileManager fileManager = new FileManager();
        fileManager.setFile(args[0]);

        CollectionManager collectionManager = new CollectionManager(fileManager);

        fileManager.setCollectionManager(collectionManager);

        Invoker invoker = new Invoker(null, console);

        CommandManager commandManager = new CommandManager(console, collectionManager);
        commandManager.addCommand("execute_script", new ExecuteScript(console, invoker));
        invoker.setCommandManager(commandManager);


        console.setInvoker(invoker);
        console.setCollectionManager(collectionManager);
        console.setScanner(new Scanner(System.in));

        collectionManager.setConsole(console);


        console.launch();
    }
}