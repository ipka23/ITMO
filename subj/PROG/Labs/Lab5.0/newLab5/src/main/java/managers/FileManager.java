package managers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import models.MusicBand;
import utility.Console;

import java.io.*;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

public class FileManager {
    private static class DateAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            try {
                return dateFormat.parse(jsonElement.getAsString());
            } catch (ParseException e) {
                throw new JsonParseException(e);
            }
        }
        /**
         * Метод для сериализации объекта типа Date в объект JSON
         *
         * @param date объект типа Date
         * @param type тип объекта
         * @param jsonSerializationContext контекст сериализации
         * @return объект JSON, содержащий дату в виде строки
         */
        @Override
        public JsonElement serialize(Date date, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(dateFormat.format(date));
        }
    }

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(Date.class, new DateAdapter())
            .create();
    private final String PATH;
    private final Console CONSOLE;

    public FileManager(String path, Console console) {
        this.PATH = path;
        this.CONSOLE = console;
    }

    /**
     * Метод для чтения коллекции MusicBand из файла
     *
     * @return коллекция MusicBand, загруженная из файла
     */
    public Collection<MusicBand> readCollectionFromFile() {
        HashSet<MusicBand> collection = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(PATH), "UTF-8"))) {
            StringBuilder s = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    s.append(line);
                }
                if (s.isEmpty()) {
                    s.append("[]");
                    break;
                }
            }
            Type collectionType = new TypeToken<HashSet<MusicBand>>() {}.getType();
            collection = gson.fromJson(s.toString(), collectionType);
            CONSOLE.println("Коллекция была успешно загружена из файла!");
        } catch (FileNotFoundException e) {
            CONSOLE.printError("Файл не найден: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            CONSOLE.printError("Неподдерживаемая кодировка: " + e.getMessage());
        } catch (IOException e) {
            CONSOLE.printError("Ошибка ввода-вывода: " + e.getMessage());
        }
        return collection;
    }

    /**
     * Метод для записи коллекции MusicBand в файл
     *
     * @param collection коллекция MusicBand для сохранения
     */
    public void writeCollectionToFile(HashSet<MusicBand> collection) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(PATH), "UTF-8"))) {
            String json = gson.toJson(collection);
            writer.write(json);
        } catch (IOException e) {
            CONSOLE.printError("Ошибка записи в файл: " + e.getMessage());
        }
    }
}