package managers;

import entities.MusicBand;
import utility.CollectionType;
import utility.exceptions.ExitException;
import utility.interfaces.Console;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
// FileManager, Console
public class CollectionManager {
    private Collection<MusicBand> collection = new HashSet<>();
    private Map<Long, MusicBand> musicBandsMap = new HashMap<>();
    private LocalDateTime initTime;
    private LocalDateTime lastSaveTime;
    private FileManager fileManager;
    private Console console;
    private long free_id;

    public CollectionManager(FileManager fileManager, Console console) {
        this.fileManager = fileManager;
        this.console = console;
        free_id = 1;
        initTime = LocalDateTime.now();
    }
    public CollectionManager(FileManager fileManager) {
        this.fileManager = fileManager;
        free_id = 1;
        initTime = LocalDateTime.now();
    }

    public CollectionManager() {

    }

    public void setMusicBandsMap(Map<Long, MusicBand> musicBandsMap) {
        this.musicBandsMap = musicBandsMap;
    }

    public void setInitTime(LocalDateTime initTime) {
        this.initTime = initTime;
    }

    public void setLastSaveTime(LocalDateTime lastSaveTime) {
        this.lastSaveTime = lastSaveTime;
    }

    public void setFree_id(long free_id) {
        this.free_id = free_id;
    }

    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }
    public long getFreeId() {
        while (musicBandsMap.get(free_id) != null) free_id++;
        return free_id;
    }


    public void saveCollection() {
        fileManager.saveCollectionToFile();
        lastSaveTime = LocalDateTime.now();
    }

    public void addMusicBand(MusicBand musicBand){
        long id = getFreeId();
        musicBandsMap.put(id, musicBand);
        musicBand.setCreationDate(LocalDate.now());
        musicBand.setId(id);
        collection.add(musicBand);
        saveCollection();
    }

    public Collection<MusicBand> getCollection() {
        return collection;
    }

    public MusicBand getMax() {
        return Collections.max(collection);
    }

    public MusicBand getMin() {
        return Collections.min(collection);
    }

    public void setCollection(Collection<MusicBand> collection) {
        this.collection = collection;
        musicBandsMap.clear();
        for (MusicBand band : collection) {
            musicBandsMap.put(band.getId(), band);
        }
    }


    public MusicBand getMusicBandById(long id) {
        return musicBandsMap.get(id);
    }

    public void removeByID(long id) {
        MusicBand band = getMusicBandById(id);
        musicBandsMap.remove(id);
        collection.remove(band);
    }

    public void chooseTypeOfCollection() {
        String userCommand = "";
        try {
            while (true) {
                console.print(CollectionType.choosingTypePrompt());
                userCommand = console.nextLine();
                userCommand = userCommand.toLowerCase().trim();
                switch (userCommand) {
                    case "exit":
                        throw new ExitException();
                    case "":
                        continue;
                    case "1", "hashset":
                        fileManager.setHashSet();
                        console.println("Тип коллекции - HashSet");
                        collection = new HashSet<>();
                        break;
                    case "2", "linkedlist":
                        fileManager.setLinkedList();
                        console.println("Тип коллекции - LinkedList");
                        collection = new LinkedList<>();
                        break;
                    default:
                        continue;
                }
                fileManager.loadCollectionFromFile("MusicBands.json");
                break;
            }
        } catch (ExitException e) {
            console.println(e.getMessage());
            System.exit(0);
        }
    }
    /**
     * Метод для получения информации о коллекции
     *
     * @return информация о коллекции в виде строки
     */
    public String info() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        StringBuilder info = new StringBuilder();
        info.append("--------------------------------Информация о коллекции--------------------------------\n");
        info.append("Тип коллекции: ").append(collection.getClass()).append("\n");
        info.append("Дата инициализации: ").append(initTime != null ? initTime.format(formatter) : "null").append("\n");
        info.append("Дата последнего сохранения: ").append(lastSaveTime != null ? lastSaveTime.format(formatter) : "null").append("\n");
        info.append("Количество элементов: ").append(collection.size());
        return info.toString();
    }

    /**
     * Возвращает строковое представление коллекции MusicBand в виде JSON строки
     *
     * @return строковое представление коллекции MusicBand
     */
    @Override
    public String toString() {
        if (collection.isEmpty()) return "Коллекция пуста!";
        StringBuilder s = new StringBuilder();
        for (MusicBand band : collection) {
            s.append(band).append("\n");
        }
        return s.substring(0, s.length() - 2);
    }

    public void setConsole(Console console) {
        this.console = console;
    }
}
