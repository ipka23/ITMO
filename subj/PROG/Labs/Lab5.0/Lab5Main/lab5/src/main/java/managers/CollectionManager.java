package managers;

import models.MusicBand;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Данный класс отвечает за управление коллекцией объектов MusicBand
 *
 * @author ipka23
 */
public class CollectionManager {
    private long freeId = 1;
    private Map<Long, MusicBand> musicBandsHashMap = new HashMap<>();
    private HashSet<MusicBand> collection = new HashSet<>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private final DumpManager dumpManager;

    /**
     * Конструктор
     *
     * @param dumpManager объект DumpManager для сохранения и загрузки коллекции
     */
    public CollectionManager(DumpManager dumpManager) {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.dumpManager = dumpManager;
        loadFromFile();
    }

    /**
     * Метод для получения коллекции MusicBand
     *
     * @return коллекция MusicBand
     */
    public HashSet<MusicBand> getCollection() {
        return collection;
    }

    /**
     * Метод для получения объекта MusicBand по его id
     *
     * @param id идентификатор MusicBand
     * @return объект MusicBand
     */
    public MusicBand getMusicBandById(long id) {
        return musicBandsHashMap.get(id);
    }

    /**
     * Метод для проверки наличия объекта MusicBand в коллекции по его id
     *
     * @param band объект MusicBand
     * @return true, если объект присутствует в коллекции, false в противном случае
     */
    public boolean isContain(MusicBand band) {
        return band != null && musicBandsHashMap.containsKey(band.getId());
    }

    /**
     * Метод для получения объекта MusicBand с максимальным значением
     *
     * @return объект MusicBand с максимальным значением
     */
    public MusicBand getMax() {
        return Collections.max(collection);
    }

    /**
     * Метод для получения объекта MusicBand с минимальным значением
     *
     * @return объект MusicBand с минимальным значением
     */
    public MusicBand getMin() {
        return Collections.min(collection);
    }

    /**
     * Метод для получения свободного id для нового объекта MusicBand
     *
     * @return свободный id
     */
    public long getFreeId() {
        while (getMusicBandById(++freeId) != null) ;
        return freeId;
    }

    /**
     * Метод для добавления объекта MusicBand в коллекцию
     *
     * @param band объект MusicBand
     * @return true, если объект был успешно добавлен, false в противном случае
     */
    public boolean add(MusicBand band) {
        if (isContain(band)) return false;
        musicBandsHashMap.put(band.getId(), band);
        collection.add(band);
        lastInitTime = LocalDateTime.now();
        return true;
    }

    /**
     * Метод для удаления объекта MusicBand из коллекции по его id
     *
     * @param id идентификатор MusicBand
     * @return true, если объект был успешно удален, false в противном случае
     */
    public boolean removeByID(long id) {
        MusicBand band = getMusicBandById(id);
        if (band == null) return false;
        musicBandsHashMap.remove(id);
        collection.remove(band);
        return true;
    }

    /**
     * Метод для сохранения коллекции MusicBand в файл
     */
    public void saveCollection() {
        dumpManager.writeCollection(collection);
        lastSaveTime = LocalDateTime.now();
    }

    /**
     * Метод для загрузки коллекции MusicBand из файла
     */
    private void loadFromFile() {
        collection = new HashSet<>(dumpManager.readCollection());
        lastInitTime = LocalDateTime.now();
        for (MusicBand band : collection) {
            musicBandsHashMap.put(band.getId(), band);
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
        info.append("Дата инициализации: ").append(lastInitTime != null ? lastInitTime.format(formatter) : "null").append("\n");
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
}
