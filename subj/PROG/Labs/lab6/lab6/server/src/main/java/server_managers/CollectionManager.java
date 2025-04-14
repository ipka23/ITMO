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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

// FileManager, Console
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
    private StandartConsole console;
    private long freeId;


    public CollectionManager(FileManager fileManager, StandartConsole console) {
        this.fileManager = fileManager;
        this.console = console;
        freeId = 1;
        initTime = LocalDateTime.now();
    }

    public CollectionManager(FileManager fileManager) {
        this.fileManager = fileManager;
        freeId = 1;
        initTime = LocalDateTime.now();
    }


    public void saveCollection() {
        fileManager.saveCollectionToFile();
        lastSaveTime = LocalDateTime.now();
    }

    public Response addMusicBand(MusicBand musicBand) {
        long id = getFreeId();
        if (musicBandsMap.containsValue(musicBand)) {
            return new Response(true, "Музыкальная группа не была добавлена, т.к. такая группа уже есть в коллекции!");
        }
        musicBandsMap.put(id, musicBand);
        musicBand.setCreationDate(LocalDate.now());
        musicBand.setId(id);
        collection.add(musicBand);
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
        while (musicBandsMap.get(freeId) != null) freeId++;
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



}
