import managers.DumpManager;
import models.Ask;
import models.MusicBand;
import managers.*;
import utility.StandartConsole;

public class BadCommands {
    public static void main(String[] args) throws Ask.AskBreak {
        var fileName = "MusicBands.json";
        var console = new StandartConsole();
        var dumpManager = new DumpManager(fileName, console);
        var collectionManager = new CollectionManager(dumpManager);
//        if (!collectionManager.init()) System.exit(1);

        while (true) {
            String[] userCommand = {"", ""};
            console.printPrompt();
            userCommand = (console.readln().trim() + " ").split(" ", 2);
            userCommand[1] = userCommand[1].trim();
            if (userCommand[0].equals("MusicBands.json")) {
                dumpManager.readCollection();
            }
            switch (userCommand[0]) {
                case "":
                    break;
                case "help":
                    console.println("help, info, save, add, exit");
                    break;
                case "info":
                    console.println(collectionManager.toString());
                    break;
                case "save":
                    collectionManager.saveCollection();
                    break;
                case "add":
                    console.println("* Создание новой MusicBand:");
                    MusicBand band = Ask.askMusicBand(console, collectionManager.getFreeId());
                    if (band != null && band.isValid()) {
                        collectionManager.add(band);
                        console.println("MusicBand успешно добавлена!");
                    } else {
                        console.printError("Поля MusicBand не валидны! MusicBand не создана!");
                    }
                    break;
                case "exit":
                    return;
                default:
                    console.printError("Команда '" + userCommand[0] +
                            "' не найдена. Наберите 'help' для справки");

            }
        }
    }
}
