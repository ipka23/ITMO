package managers;

import models.CollectionType;
import models.MusicBand;
import utility.Console;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CollectionManager {
    private long id = 0;
    private Map<Long, MusicBand> musicBandsMap = new HashMap<>();
    private Collection<MusicBand> musicBands;
    private final FileManager FILE_MANAGER;
    private LocalDateTime lastSaveTime;
    private LocalDateTime initTime;
    private Console CONSOLE;

    public CollectionManager(FileManager fileManager, Console console) {
        lastSaveTime = null;
        initTime = null;
        this.FILE_MANAGER = fileManager;
        this.CONSOLE = console;
        loadCollectionFromFile();
    }


//    public void useHashSet(boolean b) {
//        if (b) {
//            musicBands = new HashSet<>();
//        } else {
//            musicBands = new LinkedList<>();
//        }
//        loadCollectionFromFile();
//    }


    public long getId() {
        do {
            id++;
        } while (musicBandsMap.containsKey(id));
        return id;
    }

    public Collection<MusicBand> getMusicBands() {
        return musicBands;
    }

    public void add(MusicBand musicBand) {
        musicBandsMap.put(musicBand.getId(), musicBand);
        musicBands.add(musicBand);
    }



    public void loadCollectionFromFile() {
        Collection<MusicBand> loadedCollection = FILE_MANAGER.readCollectionFromFile();
        if (loadedCollection != null) {
            if (musicBands instanceof HashSet) {
                musicBands = new HashSet<>(loadedCollection);
            } else {
                musicBands = new LinkedList<>(loadedCollection);
            }
        } else {
            if (musicBands instanceof HashSet) {
                musicBands = new HashSet<>();
            } else {
                musicBands = new LinkedList<>();
            }
        }
        initTime = LocalDateTime.now();
        for (MusicBand band : musicBands) {
            musicBandsMap.put(band.getId(), band);
        }
    }

    public void chooseTypeOfCollection() {
        try {
            String userCommand = "";
            CONSOLE.println(CollectionType.names());
            while (true) {
                CONSOLE.print("(1/2): ");
                userCommand = CONSOLE.nextLine();
                userCommand = userCommand.toLowerCase().trim();
                switch (userCommand) {
                    case "exit":
                        return;
                    case "":
                        continue;
                    case "1":
                        CONSOLE.println("HashSet");
                        musicBands = new HashSet<>();
                        break;
                    case "2":
                        CONSOLE.println("LinkedList");
                        musicBands = new LinkedList<>();
                        break;
                    default:
                        continue;
                }
                loadCollectionFromFile();
                break;
            }
        } catch (NoSuchElementException e) {}
    }

    public void saveCollectionToFile() {
        FILE_MANAGER.writeCollectionToFile(musicBands);
        lastSaveTime = LocalDateTime.now();
    }

    /**
     * Метод для получения объекта MusicBand с максимальным значением
     *
     * @return объект MusicBand с максимальным значением
     */
    public MusicBand getMax() {
        return Collections.max(musicBands);
    }

    /**
     * Метод для получения объекта MusicBand с минимальным значением
     *
     * @return объект MusicBand с минимальным значением
     */
    public MusicBand getMin() {
        return Collections.min(musicBands);
    }
    /**
     * Метод для получения объекта MusicBand по его id
     *
     * @param id идентификатор MusicBand
     * @return объект MusicBand
     */
    public MusicBand getMusicBandById(long id) {
        return musicBandsMap.get(id);
    }


    public boolean removeByID(long id) {
        MusicBand band = getMusicBandById(id);
        if (band == null) return false;
        musicBandsMap.remove(id);
        musicBands.remove(band);
        return true;
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
        info.append("Тип коллекции: ").append(musicBands.getClass()).append("\n");
        info.append("Дата инициализации: ").append(initTime != null ? initTime.format(formatter) : "null").append("\n");
        info.append("Дата последнего сохранения: ").append(lastSaveTime != null ? lastSaveTime.format(formatter) : "null").append("\n");
        info.append("Количество элементов: ").append(musicBands.size());
        return info.toString();
    }

    /**
     * Возвращает строковое представление коллекции MusicBand в виде JSON строки
     *
     * @return строковое представление коллекции MusicBand
     */
    @Override
    public String toString() {
        if (musicBands.isEmpty()) return "Коллекция пуста!";
        StringBuilder s = new StringBuilder();
        for (MusicBand band : musicBands) {
            s.append(band).append("\n");
        }
        return s.substring(0, s.length() - 2);
    }
}
