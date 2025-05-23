package server_managers;

import common_entities.MusicBand;
import common_utility.network.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import server_utility.consoles.StandartConsole;
import server_utility.database.StatementValue;

import java.sql.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// FileManager, Console, DatabaseManager
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CollectionManager {
    private volatile Collection<MusicBand> collection = ConcurrentHashMap.newKeySet();
    private volatile Map<Long, MusicBand> musicBandsMap = new ConcurrentHashMap<>();
    private LocalDateTime initTime;
    private LocalDateTime lastSaveTime;
    private DatabaseManager databaseManager;
    private UserManager userManager;
    private StandartConsole console;
    private Logger log = LoggerFactory.getLogger("CollectionManager");
    private long freeId;


    public CollectionManager(StandartConsole console) {
        this.console = console;
        initTime = LocalDateTime.now();
    }


    public CollectionManager(DatabaseManager databaseManager, StandartConsole console) {
        this.databaseManager = databaseManager;
        this.console = console;
        databaseManager.loadCollectionFromDB();
    }


    public void setDatabaseManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        databaseManager.loadCollectionFromDB();
    }


    public void saveCollection() {
//        databaseManager.saveCollectionToDB();
        lastSaveTime = LocalDateTime.now();
    }


    public Response addMusicBand(MusicBand musicBand) {
        try (PreparedStatement ps = databaseManager.getConnection().prepareStatement("SELECT id FROM musicbands WHERE name = ?")) {
            databaseManager.insertIntoDB(musicBand);
            ps.setString(1, musicBand.getName());
            ps.execute();
            ResultSet rs = ps.getResultSet();
            rs.next();
            long id = rs.getLong(1);
            musicBand.setId(id);


            musicBandsMap.put(id, musicBand);
            collection.add(musicBand);

            saveCollection();
            return new Response(false, "Музыкальная группа \"" + musicBand.getName() + "\" была успешно добавлена!");
        } catch (SQLException e) {
            return new Response(false, "Музыкальная группа \"" + musicBand.getName() + "\" не была добавлена: " + e.getMessage());
        }
    }

    public Response addMusicBandIfMin(MusicBand newBand) {
        if (newBand.getBestAlbum().getSales() < getMin().getBestAlbum().getSales()) {
            if (!addMusicBand(newBand).getExitStatus()) {
                return new Response(false, "В коллекцию была добавлена музыкальная группа \"" + newBand.getName() + "\", количество продаж её лучшего альбома меньше чем у группы с минимальным количеством продаж!");
            } else return addMusicBand(newBand);
        } else {
            return new Response(false, "Музыкальная группа \"" + newBand.getName() + "\" не была добавлена в коллекцию, т. к. количество продаж её лучшего альбома больше чем у группы с минимальным количеством продаж!");
        }
    }

    public Response addMusicBandIfMax(MusicBand newBand) {
        if (newBand.getBestAlbum().getSales() > getMax().getBestAlbum().getSales()) {
            if (!addMusicBand(newBand).getExitStatus()) {
                return new Response(false, "В коллекцию была добавлена музыкальная группа \"" + newBand.getName() + "\" , количество продаж лучшего альбома которой больше чем у группы с максимальным количеством продаж!");
            } else return addMusicBand(newBand);
        } else {
            return new Response(false, "Музыкальная группа \"" + newBand.getName() + "\" не была добавлена в коллекцию, т. к. количество продаж её лучшего альбома меньше чем у группы с максимальным количеством продаж!");
        }
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
        collection.stream().forEach(band -> musicBandsMap.put(band.getId(), band));
    }


    public MusicBand getMusicBandById(long id) {
        return musicBandsMap.get(id);
    }

    public void removeByID(long id) {
        try {
            MusicBand band = getMusicBandById(id);
            musicBandsMap.remove(id);
            collection.remove(band);
            PreparedStatement ps = databaseManager.getConnection().prepareStatement(StatementValue.REMOVE_MUSIC_BAND_BY_ID.toString());
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.info(e.getMessage());
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
        info.append("==========================\n: Информация о коллекции :\n==========================\n");
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
        collection.stream().forEach(band -> s.append(band.toString()).append("\n"));
        return s.substring(0, s.length() - 2);
    }

    public void clearCollection() {
        collection.clear();
        try {
            Connection connection = databaseManager.getConnection();
            connection.createStatement().execute(StatementValue.TRUNCATE_MUSIC_BANDS.toString());
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }


}
