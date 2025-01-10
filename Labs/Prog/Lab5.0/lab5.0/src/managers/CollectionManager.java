package managers;

import models.Aboba;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class CollectionManager {
    private int currentId = 1;
    private Map<Integer, Aboba> abobus = new HashMap<>();
    private LinkedList<Aboba> collection = new LinkedList<Aboba>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private final DumpManager dumpManager;

    public CollectionManager(DumpManager dumpManager) {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.dumpManager = dumpManager;
    }

    /**
     * @return Последнее время инициализации.
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * @return Последнее время сохранения.
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * @return коллекция.
     */
    public LinkedList<Aboba> getCollection() {
        return collection;
    }

    /**
     * Получить Aboba по ID
     */
    public Aboba byId(int id) { return abobus.get(id); }

    /**
     * Содержит ли колекции Aboba
     */
    public boolean isСontain(Aboba e) { return e == null || byId(e.getId()) != null; }

    /**
     * Получить свободный ID
     */
    public int getFreeId() {
        while (byId(++currentId) != null);
        return currentId;
    }

    /**
     * Добавляет Aboba
     */
    public boolean add(Aboba a) {
        if (isСontain(a)) return false;
        abobus.put(a.getId(), a);
        collection.add(a);
        update();
        return true;
    }


    /**
     * Обновляет Aboba
     */
    public boolean update(Aboba a) {
        if (!isСontain(a)) return false;
        collection.remove(byId(a.getId()));
        abobus.put(a.getId(), a);
        collection.add(a);
        update();
        return true;
    }

    /**
     * Удаляет Aboba по ID
     */
    public boolean remove(long id) {
        var a = byId((int) id);
        if (a == null) return false;
        abobus.remove(a.getId());
        collection.remove(a);
        update();
        return true;
    }

    /**
     * Фиксирует изменения коллекции
     */
    public void update() {
        Collections.sort(collection);
    }

    public boolean init() {
        collection.clear();
        abobus.clear();
        dumpManager.readCollection(collection);
        lastInitTime = LocalDateTime.now();
        for (var e : collection)
            if (byId(e.getId()) != null) {
                collection.clear();
                abobus.clear();
                return false;
            } else {
                if (e.getId()>currentId) currentId = e.getId();
                abobus.put(e.getId(), e);
            }
        update();
        return true;
    }

    /**
     * Сохраняет коллекцию в файл
     */
    public void saveCollection() {
        dumpManager.writeCollection(collection);
        lastSaveTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        if (collection.isEmpty()) return "Коллекция пуста!";

        StringBuilder info = new StringBuilder();
        for (var aboba : collection) {
            info.append(aboba+"\n\n");
        }
        return info.toString().trim();
    }
}

