package managers;

import models.MusicBand;

import java.time.LocalDateTime;
import java.util.*;


public class CollectionManager {
    private int freeId = 1;
    private Map<Integer, MusicBand> musicBandsHashMap = new HashMap<>();
    private HashSet<MusicBand> collection = new HashSet<>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private final DumpManager dumpManager;

    public CollectionManager(DumpManager dumpManager) {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.dumpManager = dumpManager;
    }

    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    public HashSet<MusicBand> getCollection() {
        return collection;
    }

    public MusicBand getMusicBandById(int id) {
        return musicBandsHashMap.get(id);
    }

    public boolean isContain(MusicBand band) {
        return band != null && musicBandsHashMap.containsKey((int) band.getId());
    }


    public int getFreeId() {
        while (getMusicBandById(++freeId) != null);
        return freeId;
    }

    public boolean add(MusicBand band) {
        if (isContain(band)) return false;
        musicBandsHashMap.put((int)band.getId(), band);
        collection.add(band);
//        update();
        return true;
    }
//
//    public boolean update(MusicBand band) {
//        if (!isContain(band)) return false;
//        collection.remove(getMusicBandById(((int)band.getId())));
//        musicBand.put((int)band.getId(), band);
//        collection.add(band);
//        update();
//        return true;
//    }

//    public void update() {
//        Collections.sort(collection);
//    }
    public void saveCollection() {
        dumpManager.writeCollection(collection);
        lastSaveTime = LocalDateTime.now();
    }
    public boolean remove(MusicBand band) {
        if (getMusicBandById((int)band.getId()) == null) return false;
        musicBandsHashMap.remove((int)band.getId());
        collection.remove(band);
//        update();
        return true;
    }
//
//    public boolean init() {
//        collection.clear();
//        musicBandsHashMap.clear();
//        dumpManager.readCollection();
//        lastInitTime = LocalDateTime.now();
//        for (var e : collection)
//            if (getMusicBandById((int)e.getId()) != null) {
//                collection.clear();
//                musicBandsHashMap.clear();
//                return false;
//            } else {
//                if (e.getId()>freeId) freeId = (int) e.getId();
//                musicBandsHashMap.put((int)e.getId(), e);
//            }
//        update();
//        return true;
//    }



    @Override
    public String toString() {
        if (collection.isEmpty()) return "Коллекция пуста!";

        StringBuilder info = new StringBuilder();
        info.append("----Информация о коллекции----\n");
        info.append("Тип коллекции: ").append(collection.getClass()).append("\n");
        info.append("Дата инициализации: ").append(lastInitTime).append("\n");
        info.append("Дата последнего сохранения: ").append(lastSaveTime).append("\n");
        info.append("Количество элементов: ").append(collection.size()).append("\n");

        return info.toString();
    }
}
