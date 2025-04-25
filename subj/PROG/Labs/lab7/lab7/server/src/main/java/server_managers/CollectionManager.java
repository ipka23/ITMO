package server_managers;

import common_entities.MusicBand;
import common_utility.exceptions.ExitClientException;
import common_utility.network.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import server_utility.CollectionType;
import server_utility.consoles.StandartConsole;
import server_utility.database.StatementValue;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// FileManager, Console, DatabaseManager
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CollectionManager {
    private Collection<MusicBand> collection = new HashSet<>();
    private Map<Long, MusicBand> musicBandsMap = new HashMap<>();
    private LocalDateTime initTime;
    private LocalDateTime lastSaveTime;
    private FileManager fileManager;
    private DatabaseManager databaseManager;
    private StandartConsole console;
    private Logger log = LoggerFactory.getLogger("CollectionManager");
    private long freeId;


    public CollectionManager(FileManager fileManager, StandartConsole console) {
        this.fileManager = fileManager;
        this.console = console;
        initTime = LocalDateTime.now();
    }

    public CollectionManager(FileManager fileManager) {
        this.fileManager = fileManager;
        initTime = LocalDateTime.now();
    }

    public CollectionManager(DatabaseManager databaseManager, StandartConsole console) {
        this.databaseManager = databaseManager;
        this.console = console;
        collection = databaseManager.loadCollectionFromDB();
    }

    public CollectionManager(StandartConsole console) {
        this.console = console;
    }

    public void setDatabaseManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        collection = databaseManager.loadCollectionFromDB();
    }


    public void saveCollection() {
//        databaseManager.saveCollectionToDB();

        lastSaveTime = LocalDateTime.now();
    }
    public Response addMusicBand(MusicBand musicBand) {
        long id = getFreeId();
        if (musicBandsMap.containsValue(musicBand)) {
            return new Response(false, "Музыкальная группа не была добавлена, т.к. такая группа уже есть в коллекции!");
        }
        musicBandsMap.put(id, musicBand);
        musicBand.setCreationDate(LocalDate.now());
        musicBand.setId(id);
        collection.add(musicBand);
        databaseManager.insertIntoDB(musicBand);
        saveCollection();
        return new Response(false, "Музыкальная группа была успешно добавлена!");
    }

    public Response addMusicBandIfMin(MusicBand newBand) {
        if (newBand.getBestAlbum().getSales() < getMin().getBestAlbum().getSales()) {
            if (!addMusicBand(newBand).getExitStatus()) {
                return new Response(false, "В коллекцию была добавлена музыкальная группа, количество продаж лучшего альбома которой меньше чем у группы с минимальным количеством продаж!");
            } else return addMusicBand(newBand);
        } else {
            return new Response(false, "Музыкальная группа не была добавлена в коллекцию, т. к. количество продаж её лучшего альбома больше чем у группы с минимальным количеством продаж!");
        }
    }

    public Response addMusicBandIfMax(MusicBand newBand) {
        if (newBand.getBestAlbum().getSales() > getMax().getBestAlbum().getSales()) {
            if (!addMusicBand(newBand).getExitStatus()) {
                return new Response(false, "В коллекцию была добавлена музыкальная группа, количество продаж лучшего альбома которой больше чем у группы с максимальным количеством продаж!");
            } else return addMusicBand(newBand);
        } else {
            return new Response(false, "Музыкальная группа не была добавлена в коллекцию, т. к. количество продаж её лучшего альбома меньше чем у группы с максимальным количеством продаж!");
        }
    }


    public long getFreeId() {
        Connection connection =  databaseManager.getConnection();
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(StatementValue.GET_MAX_ID.toString());
            ResultSet rs = stmt.getResultSet();
            rs.next();
            freeId = rs.getLong(1) + 1;


            if (freeId != 1){
                PreparedStatement ps = connection.prepareStatement(StatementValue.SYNC_SEQUENCE_ID.toString());
                ps.setLong(1, freeId - 1);
                ps.executeUpdate();
            } else {
                connection.createStatement().execute(StatementValue.RESTART_ID_SEQ.toString());
            }
        } catch (SQLException e) {
            log.info(e.getMessage());
        }
         return freeId;
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
//        for (MusicBand band : collection) {
//            musicBandsMap.put(band.getId(), band);
//        }
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
        collection.stream().forEach(band -> s.append(band.toString()).append("\n"));
        return s.substring(0, s.length() - 2);
    }



    /** @deprecated */
    @Deprecated
    public Response chooseTypeOfCollection() {
        String userCommand = "";
        Response response = null;
        try {
            while (true) {
                console.print(CollectionType.choosingTypePrompt());
                userCommand = console.nextLine();
                userCommand = userCommand.toLowerCase().trim();
                switch (userCommand) {
                    case "exit":
                        throw new ExitClientException();
                    case "":
                        continue;
                    case "1", "hashset":
                        fileManager.setHashSet();
                        collection = new HashSet<>();
                        fileManager.loadCollectionFromFile();
                        response = new Response(true, "Тип коллекции - HashSet");
                    case "2", "linkedlist":
                        fileManager.setLinkedList();
                        collection = new LinkedList<>();
                        fileManager.loadCollectionFromFile();
                        response = new Response(true, "Тип коллекции - LinkedList");
                    default:
                        continue;
                }
            }
        } catch (ExitClientException e) {
            response = new Response(true, "\n" + e.getMessage());
        }
        return response;
    }

    public void clearCollection() {
        collection.clear();
        try {
            Connection connection =  databaseManager.getConnection();
            connection.createStatement().execute(StatementValue.TRUNCATE_MUSIC_BANDS.toString());
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }


}
