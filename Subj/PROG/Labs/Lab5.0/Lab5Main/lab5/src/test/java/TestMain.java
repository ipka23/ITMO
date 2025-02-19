import commands.Add;
import managers.CollectionManager;
import managers.CommandManager;
import managers.DumpManager;
import utility.Console;
import utility.StandartConsole;

public class TestMain {
    public static void main(String[] args) {
        Console console = new StandartConsole();
        DumpManager dumpManager = new DumpManager("src/test/resources/MusicBands.json", console);
        CollectionManager collectionManager = new CollectionManager(dumpManager);
        CommandManager commandManager = new CommandManager();

        commandManager.addCommand("add", new Add(console, collectionManager));

        String command = console.readln(); // ввод пользователя
        commandManager.getCommandsMap().get(command).execute(new String[]{});

    }
}
