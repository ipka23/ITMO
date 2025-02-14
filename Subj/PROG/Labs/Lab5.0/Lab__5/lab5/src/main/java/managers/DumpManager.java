package managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.MusicBand;
import utility.Console;
import utility.LocalDateAdapter;

import java.io.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.PriorityQueue;


public class DumpManager {
    private Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).serializeNulls().create(); //.registerTypeAdapter
    private String fileName;
    private Console console;
    public DumpManager(String fileName, Console console) {
//        if (!(new File(fileName).exists())) {
//            fileName = "../" + fileName;
//        }

        this.fileName = fileName;
        this.console = console;
    }

    public void writeCollection(Collection<MusicBand> collection) { // ?? возможно ошибка ??
        try (FileWriter fw = new FileWriter(fileName)) {
            var collectionType = new TypeToken<Collection<MusicBand>>(){}.getType();
            String json = gson.toJson(collection, collectionType);
            fw.write(json);
        } catch (IOException e) {
            console.printError("Не удается открыть указанный файл!"); //console.printError(e.getMessage());
        }
    }

    public Collection<MusicBand> readCollection() {
        if (fileName != null && !fileName.isEmpty()) {
            try (var fileReader = new FileReader(fileName)) {
                var collectionType = new TypeToken<PriorityQueue<MusicBand>>(){}.getType();
                BufferedReader reader = new BufferedReader(fileReader);
                StringBuilder jsonString = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        jsonString.append(line);
                    }
                    if (jsonString.length() == 0) {
                        jsonString = new StringBuilder("[]");
                    }
                }
                PriorityQueue<MusicBand> collection = gson.fromJson(jsonString.toString(), collectionType);
                System.out.println("Коллекция была успешно загружена из файла!");
                return collection;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new PriorityQueue<>(); // чтобы не возникало NullPointerException, и можно было просто работать с пустой коллекцией, например добавлять в неё новые элементы
    }
}
