package server_managers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import common_entities.MusicBand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import server_utility.interfaces.Console;

import java.io.*;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//CollectionManager, Console, String filename
public class FileManager {
    private CollectionManager collectionManager;
    private Console console;
    private String fileName;
    private Type collectionType; //= new TypeToken<LinkedList<MusicBand>>(){}.getType();


    public FileManager(CollectionManager collectionManager, Console console, String fileName) {
        this.collectionManager = collectionManager;
        this.console = console;
        this.fileName = fileName;

    }



    public void setFile(String fileName) {
        this.fileName = fileName;
        loadCollectionFromFile();
    }


    public static class DateAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {
        private final DateFormat dateFormat;

        public DateAdapter() {
            dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        }

        @Override
        public JsonElement serialize(Date date, Type type, JsonSerializationContext context) {
            return new JsonPrimitive(dateFormat.format(date));
        }

        @Override
        public Date deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
            try {
                return dateFormat.parse(json.getAsString());
            } catch (ParseException e) {
                throw new JsonParseException(e);
            }
        }
    }
    public static class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
        private final DateTimeFormatter formatter;

        public LocalDateAdapter() {
            formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        }

        @Override
        public JsonElement serialize(LocalDate localDate, Type type, JsonSerializationContext context) {
            return new JsonPrimitive(localDate.format(formatter));
        }

        @Override
        public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
            return LocalDate.parse(json.getAsString(), formatter);
        }
    }

    public void setHashSet() {
        this.collectionType = new TypeToken<HashSet<MusicBand>>(){}.getType();
    }

    public void setLinkedList() {
        this.collectionType = new TypeToken<LinkedList<MusicBand>>(){}.getType();
    }

    public void loadCollectionFromFile() {
        try (Scanner scanner = new Scanner(new FileReader(fileName))) {
            StringBuilder jsonString = new StringBuilder();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                jsonString.append(line);
            }

            String json = jsonString.toString();
            if (json.isEmpty()) {
                json = "[]";
            }
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .serializeNulls()
                    .registerTypeAdapter(Date.class, new DateAdapter())
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();
            collectionManager.setCollection(gson.fromJson(json, new TypeToken<HashSet<MusicBand>>(){}.getType()));
        } catch (FileNotFoundException e) {
            System.out.print("Файл \"" + fileName + "\" не найден!");
            System.exit(2);
        }
    }

    public void saveCollectionToFile() {
        try (OutputStreamWriter bufferedWriter = new OutputStreamWriter(new FileOutputStream(fileName))) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .serializeNulls()
                    .registerTypeAdapter(Date.class, new DateAdapter())
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();
            gson.toJson(collectionManager.getCollection(), bufferedWriter);
        } catch (IOException e) {}
    }
}
