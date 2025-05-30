package server_managers;

import common_entities.MusicBand;
import common_utility.localization.LanguageManager;
import common_utility.network.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import server_utility.consoles.StandartConsole;
import server_utility.database.StatementValue;

import java.io.*;
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
public class CollectionManager {
    private Collection<MusicBand> collection = ConcurrentHashMap.newKeySet();
    private Map<Long, MusicBand> musicBandsMap = new ConcurrentHashMap<>();
    private LocalDateTime initTime;
    private LocalDateTime lastSaveTime;
    private DatabaseManager databaseManager;
    private UserManager userManager;
    private StandartConsole console;
    private Logger log = LoggerFactory.getLogger("CollectionManager");
    private long freeId;

    private static final String FILE_NAME = "iniTime";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public CollectionManager(StandartConsole console) {
        try {
            this.console = console;
            File initFile = new File(FILE_NAME);

            if (!initFile.exists()) {
                initFile.createNewFile();
            }

            try (Scanner sc = new Scanner(initFile)) {
                if (sc.hasNextLine()) {
                    String line = sc.nextLine().trim();
                    if (!line.isEmpty()) {
                        initTime = LocalDateTime.parse(line, formatter);
                    } else {
                        initTime = LocalDateTime.now();
                        saveInitTime(initTime, initFile);
                    }
                } else {
                    initTime = LocalDateTime.now();
                    saveInitTime(initTime, initFile);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveInitTime(LocalDateTime time, File file) throws IOException {
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println(time.format(formatter));
        }
    }


    public void remove(MusicBand musicBand) {
        try {
            musicBandsMap.remove(musicBand.getId());
            collection.remove(musicBand);
            PreparedStatement ps = databaseManager.getConnection().prepareStatement(StatementValue.REMOVE_MUSIC_BAND_BY_ID.toString());
            ps.setLong(1, musicBand.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            log.info(e.getMessage());
        }
    }

   /* public CollectionManager(DatabaseManager databaseManager, StandartConsole console) {
        this.databaseManager = databaseManager;
        this.console = console;
        databaseManager.loadCollectionFromDB();
        initTime = LocalDateTime.now();
    }*/


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
            Response response = new Response(true, getString("bandAdded"));
            response.setMusicBand(musicBand);
            return response;
        } catch (SQLException e) {
            return new Response(false, getString("bandNotAdded"));
        }
    }

    public String getString(String key) {
        return LanguageManager.getBundle().getString(key);
    }

    public Response addMusicBandIfMin(MusicBand newBand) {
        if (newBand.getBestAlbum().getSales() < getMin().getBestAlbum().getSales()) {
            if (addMusicBand(newBand).getExitStatus()) {
                return new Response(true, getString("lowerBandAdd"));
            } else return addMusicBand(newBand);
        } else {
            return new Response(false, getString("lowerBandNotAdd"));
        }
    }

    public Response addMusicBandIfMax(MusicBand newBand) {
        if (newBand.getBestAlbum().getSales() > getMax().getBestAlbum().getSales()) {
            if (addMusicBand(newBand).getExitStatus()) {
                return new Response(true, getString("biggerBandAdd"));
            } else return addMusicBand(newBand);
        } else {
            return new Response(false, getString("biggerBandNotAdd"));
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
        info.append("\n");
//        info.append(getString("collectionInfo") + " "); // todo т.к. в DialogPane setTitle info
        info.append(getString("collectionType")+ " ").append(collection.getClass()).append("\n");
        info.append(getString("initializationDate")+ " ").append(initTime != null ? initTime.format(formatter) : "null").append("\n");
        info.append(getString("elementsCount")+ " ").append(collection.size());
        return info.toString();
    }

    /**
     * Возвращает строковое представление коллекции MusicBand в виде JSON строки
     *
     * @return строковое представление коллекции MusicBand
     */
    @Override
    public String toString() {
        if (collection.isEmpty()) return getString("collectionIsEmpty");
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
