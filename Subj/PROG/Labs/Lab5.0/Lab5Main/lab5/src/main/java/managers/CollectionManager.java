package managers;

import models.MusicBand;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class CollectionManager {
    private long freeId = 1;
    private Map<Long, MusicBand> musicBandsHashMap = new HashMap<>();
    private HashSet<MusicBand> collection = new HashSet<>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private final DumpManager dumpManager;

    public CollectionManager(DumpManager dumpManager) {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.dumpManager = dumpManager;
        loadFromFile();
    }

    public HashSet<MusicBand> getCollection() {
        return collection;
    }

    public MusicBand getMusicBandById(long id) {
        return musicBandsHashMap.get(id);
    }

    public boolean isContain(MusicBand band) {
        return band != null && musicBandsHashMap.containsKey(band.getId());
    }

    public MusicBand getMax() {
        return Collections.max(collection);
    }
    public MusicBand getMin() {
        return Collections.min(collection);
    }
    public long getFreeId() {
        while (getMusicBandById(++freeId) != null) ;
        return freeId;
    }


    public boolean add(MusicBand band) {
        if (isContain(band)) return false;
        musicBandsHashMap.put(band.getId(), band);
        collection.add(band);
        lastInitTime = LocalDateTime.now();
        return true;
    }

    public boolean removeByID(long id) {
        MusicBand band = getMusicBandById(id);
        if (band == null) return false;
        musicBandsHashMap.remove(id);
        collection.remove(band);
        return true;
    }

    public void remove(MusicBand band) {
        collection.remove(band);
    }

    public void saveCollection() {
        dumpManager.writeCollection(collection);
        lastSaveTime = LocalDateTime.now();
    }


    private void loadFromFile() {
        collection = new HashSet<>(dumpManager.readCollection());
        lastInitTime = LocalDateTime.now();
        for (MusicBand band : collection) {
            musicBandsHashMap.put(band.getId(), band);
        }
    }

    public String info() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        StringBuilder info = new StringBuilder();
        if (collection.isEmpty()) {
            info.append("----Информация о коллекции----\n");
            info.append("Тип коллекции: ").append(collection.getClass()).append("\n");
            info.append("Дата инициализации: ").append(lastInitTime != null ? lastInitTime.format(formatter) : "null").append("\n");
            info.append("Дата последнего сохранения: ").append(lastSaveTime != null ? lastSaveTime.format(formatter) : "null").append("\n");
            info.append("Количество элементов: ").append(collection.size());
        } else {
            info.append("----Информация о коллекции----\n");
            info.append("Тип коллекции: ").append(collection.getClass()).append("\n");
            info.append("Дата инициализации: ").append(lastInitTime != null ? lastInitTime.format(formatter) : "null").append("\n");
            info.append("Дата последнего сохранения: ").append(lastSaveTime != null ? lastSaveTime.format(formatter) : "null").append("\n");
            info.append("Количество элементов: ").append(collection.size());
        }
        return info.toString();
    }

    @Override
    public String toString() {
        if (collection.isEmpty()) return "Коллекция пуста!";
        StringBuilder s = new StringBuilder();
        for (MusicBand band : collection){
          s.append(band).append("\n");
        }
        return s.substring(0, s.length() - 2);
    }
}
