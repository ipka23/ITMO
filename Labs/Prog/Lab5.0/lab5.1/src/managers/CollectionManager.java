package managers;

import models.Organization;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class CollectionManager {
    private Long currentId = 1L;
    private Map<Long, Organization> organizationsMap = new HashMap<>();
    private LinkedList<Organization> collection = new LinkedList<Organization>();
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
    public LinkedList<Organization> getCollection() {
        return collection;
    }

    /**
     * Получить Aboba по ID
     */
    public Organization getOrganizationById(Long id) { return organizationsMap.get(id); }

    /**
     * Содержит ли колекции Aboba
     */
    public boolean isСontain(Organization e) { return e == null || getOrganizationById(e.getId()) != null; }

    /**
     * Получить свободный ID
     */
    public Long getFreeId() {
        while (getOrganizationById(++currentId) != null);
        return currentId;
    }

    /**
     * Добавляет Aboba
     */
    public boolean add(Organization a) {
        if (isСontain(a)) return false;
        organizationsMap.put(a.getId(), a);
        collection.add(a);
        update();
        return true;
    }


    /**
     * Обновляет Aboba
     */
    public boolean update(Organization a) {
        if (!isСontain(a)) return false;
        collection.remove(getOrganizationById(a.getId()));
        organizationsMap.put(a.getId(), a);
        collection.add(a);
        update();
        return true;
    }

    /**
     * Удаляет Aboba по ID
     */
    public boolean remove(Long id) {
        var a = getOrganizationById(id);
        if (a == null) return false;
        organizationsMap.remove(a.getId());
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
        organizationsMap.clear();
        dumpManager.readCollection(collection);
        lastInitTime = LocalDateTime.now();
        for (var e : collection)
            if (getOrganizationById(e.getId()) != null) {
                collection.clear();
                organizationsMap.clear();
                return false;
            } else {
                if (e.getId()>currentId) currentId = e.getId() ;
                organizationsMap.put(e.getId(), e);
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
        for (var organization : collection) {
            info.append(organization+"\n\n");
        }
        return info.toString().trim();
    }
    public boolean loadCollection() {
        organizationsMap.clear();
        dumpManager.readCollection(collection);
        lastInitTime = LocalDateTime.now();
        for (var e : collection)
            if (getOrganizationById(e.getId()) != null) {
                collection.clear();
                return false;
            } else {
                if (e.getId()>currentId) currentId = e.getId();
                organizationsMap.put(e.getId(), e);
            }
        update();
        return true;
    }
}