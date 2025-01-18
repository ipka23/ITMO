import managers.CollectionManager;
import managers.DumpManager;
import models.*;
import utility.Ask;
import utility.StandardConsole;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Ask.AskBreak {
        var console = new StandardConsole();

        var fileName = "db.csv"; // TODO from variable or args

        var dumpManager = new DumpManager(fileName, console);
        var collectionManager = new CollectionManager(dumpManager);
        if (!collectionManager.init()) { System.exit(1); }

        while (true) {
            String[] userCommand = {"", ""};
            console.prompt();
            userCommand = (console.readln().trim() + " ").split(" ", 2);
            userCommand[1] = userCommand[1].trim();

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
                    console.println("* Создание нового Organization:");
                    Organization organization = Ask.askOrganization(console, collectionManager.getFreeId());
                    if (organization != null && organization.validate()) {
                        collectionManager.add(organization);
                        console.println("Organization успешно добавлен!");
                    } else {
                        console.printError("Поля Aboba не валидны! Aboba не создан!");
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

    
