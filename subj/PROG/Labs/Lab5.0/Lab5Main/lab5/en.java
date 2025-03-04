package managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.MusicBand;
import utility.Console;
import utility.DateAdapter;

import java.io.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
/**
 * Данный класс отвечает за сохранение и загрузку коллекции объектов MusicBand в/из файла в формате JSON;
 * Использует библиотеку Gson для сериализации и десериализации объектов
 * @author ipka23
 */
public class DumpManager {
    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(Date.class, new DateAdapter())
            .create();

    private String env;
    private Console console;
    /**
     * Конструктор
     *
     * @param env имя переменной окружения для сохранения и загрузки коллекции
     * @param console интерфейс Console для взаимодействия с пользователем
     */
    public DumpManager(String env, Console console) {
        this.env = env;
        this.console = console;
    }
    /**
     * Метод для сохранения коллекции MusicBand в файл
     *
     * @param collection коллекция MusicBand для сохранения
     */
    public void writeCollection(HashSet<MusicBand> collection) {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(System.getenv(env)), "UTF-8")) {
            var collectionType = new TypeToken<Collection<MusicBand>>() {}.getType();
            String json = gson.toJson(collection, collectionType);
            writer.write(json);
        } catch (IOException e) {
            console.printError("Не удается открыть указанный файл!");
        }
    }
    /**
     * Метод для загрузки коллекции MusicBand из файла
     *
     * @return коллекция MusicBand, загруженная из файла
     */
    public Collection<MusicBand> readCollection() {
        if (System.getenv(env) != null && !System.getenv(env).isEmpty()) {
            try (var fileReader = new FileReader(System.getenv(env))) {
                var collectionType = new TypeToken<HashSet<MusicBand>>() {}.getType();
                BufferedReader reader = new BufferedReader(fileReader);
                StringBuilder jsonString = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        jsonString.append(line);
                    }
                    if (jsonString.isEmpty()) {
                        jsonString = new StringBuilder("[]");
                    }
                }
                HashSet<MusicBand> collection = gson.fromJson(jsonString.toString(), collectionType);
                System.out.println("Коллекция была успешно загружена из файла!");
                return collection;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new HashSet<>(); // чтобы не возникало NullPointerException, и можно было просто работать с пустой коллекцией, например добавлять в неё новые элементы
    }
}

