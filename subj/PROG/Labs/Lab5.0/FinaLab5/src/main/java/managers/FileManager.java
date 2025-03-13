package managers;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entities.Coordinates;
import entities.MusicBand;
import utility.interfaces.Console;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Scanner;

public class FileManager {
    private CollectionManager collectionManager;
    private Console console;
    public FileManager(CollectionManager collectionManager, Console console) {
        this.collectionManager = collectionManager;
        this.console = console;
    }
    public void loadCollectionFromFile(String filename) {
        try (Scanner scanner = new Scanner(new FileReader(filename))) {
            StringBuilder jsonString = new StringBuilder();
            do {
                String line = scanner.nextLine();
                jsonString.append(line);
            } while (scanner.hasNextLine());
            String json = jsonString.toString();
            Gson gson = new Gson();
            Type collectionType = new TypeToken<Collection<MusicBand>>(){}.getType();
            collectionManager.setCollection(gson.fromJson(json, collectionType));
        } catch (FileNotFoundException e) {}
    }
}
