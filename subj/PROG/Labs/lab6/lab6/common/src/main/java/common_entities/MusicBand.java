package common_entities;


import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;


/**
 * Данный класс представляет музыкальную группу и реализует интерфейс Validatable для проверки корректности данных;
 * Содержит информацию о музыкальной группе: id, название, координаты, дату создания, количество участников, количество синглов, дату основания, жанр музыки и лучший альбом
 *
 * @author ipka23
 */
public class MusicBand implements Comparable<MusicBand> {


    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long numberOfParticipants; //Поле может быть null, Значение поля должно быть больше 0
    private Long singlesCount; //Поле не может быть null, Значение поля должно быть больше 0
    private Date establishmentDate; //Поле может быть null
    private MusicGenre genre; //Поле не может быть null
    private Album bestAlbum; //Поле не может быть null

    /**
     * Конструктор
     *
     * @param id                   идентификатор музыкальной группы
     * @param name                 название музыкальной группы
     * @param coordinates          координаты группы
     * @param creationDate         дата создания группы
     * @param numberOfParticipants количество участников группы
     * @param singlesCount         количество синглов группы
     * @param establishmentDate    дата основания группы
     * @param genre                жанр музыки группы
     * @param bestAlbum            лучший альбом группы
     */
    public MusicBand(long id, String name, Coordinates coordinates, String creationDate, Long numberOfParticipants, Long singlesCount, Date establishmentDate, MusicGenre genre, Album bestAlbum) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.numberOfParticipants = numberOfParticipants;
        this.singlesCount = singlesCount;
        this.establishmentDate = establishmentDate;
        this.genre = genre;
        this.bestAlbum = bestAlbum;

    }

    public MusicBand(String name) {
        this.name = name;
    }
    public void setId(long id) {
        this.id = id;
    }


    /**
     * Конструктор
     *
     * @param name                 название музыкальной группы
     * @param coordinates          координаты группы
     * @param numberOfParticipants количество участников группы
     * @param singlesCount         количество синглов группы
     * @param establishmentDate    дата основания группы
     * @param genre                жанр музыки группы
     * @param bestAlbum            лучший альбом группы
     */
    public MusicBand(String name, Coordinates coordinates, Long numberOfParticipants, Long singlesCount, Date establishmentDate, MusicGenre genre, Album bestAlbum) {

        this.name = name;
        this.coordinates = coordinates;
        this.numberOfParticipants = numberOfParticipants;
        this.singlesCount = singlesCount;
        this.establishmentDate = establishmentDate;
        this.genre = genre;
        this.bestAlbum = bestAlbum;
    }

    public MusicBand(String name, Coordinates coordinates, Long numberOfParticipants, Long singlesCount, MusicGenre genre, Album bestAlbum) {
        this.name = name;
        this.coordinates = coordinates;
        this.numberOfParticipants = numberOfParticipants;
        this.singlesCount = singlesCount;
        this.genre = genre;
        this.bestAlbum = bestAlbum;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public MusicBand(long id, String name, Coordinates coordinates, Long numberOfParticipants, Long singlesCount, MusicGenre genre, Album bestAlbum) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.numberOfParticipants = numberOfParticipants;
        this.singlesCount = singlesCount;
        this.genre = genre;
        this.bestAlbum = bestAlbum;
    }

    /**
     * Конструктор
     *
     * @param id                   идентификатор музыкальной группы
     * @param name                 название музыкальной группы
     * @param coordinates          координаты группы
     * @param numberOfParticipants количество участников группы
     * @param singlesCount         количество синглов группы
     * @param establishmentDate    дата основания группы
     * @param genre                жанр музыки группы
     * @param bestAlbum            лучший альбом группы
     */
    public MusicBand(long id, String name, Coordinates coordinates, LocalDate creationDate, Long numberOfParticipants, Long singlesCount, Date establishmentDate, MusicGenre genre, Album bestAlbum) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.numberOfParticipants = numberOfParticipants;
        this.singlesCount = singlesCount;
        this.establishmentDate = establishmentDate;
        this.genre = genre;
        this.bestAlbum = bestAlbum;
        this.creationDate = LocalDate.now();
    }


    /**
     * Метод для получения id музыкальной группы
     *
     * @return id музыкальной группы
     */
    public long getId() {
        return id;
    }

    /**
     * Метод для получения количества продаж лучшего альбома группы
     *
     * @return количество продаж лучшего альбома группы
     */
    public Double getSales() {
        return bestAlbum.getSales();
    }


    /**
     * Метод для получения названия музыкальной группы
     *
     * @return название музыкальной группы
     */
    public String getName() {
        return name;
    }

    /**
     * Метод для получения даты основания группы
     *
     * @return дата основания группы
     */
    public Date getEstablishmentDate() {
        return establishmentDate;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Long getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public Long getSinglesCount() {
        return singlesCount;
    }

    public MusicGenre getGenre() {
        return genre;
    }

    public Album getBestAlbum() {
        return bestAlbum;
    }

    /**
     * Метод для обновления данных музыкальной группы
     *
     * @param band объект MusicBand с новыми данными
     */
    public void update(MusicBand band) {
        this.name = band.getName();
        this.coordinates = band.getCoordinates();
        this.creationDate = LocalDate.now();
        this.numberOfParticipants = band.getNumberOfParticipants();
        this.singlesCount = band.getSinglesCount();
        this.establishmentDate = band.getEstablishmentDate();
        this.genre = band.getGenre();
        this.bestAlbum = band.getBestAlbum();
    }

    /**
     * Метод для сравнения музыкальных групп по количеству продаж лучшего альбома
     *
     * @param band объект MusicBand для сравнения
     * @return результат сравнения количества продаж лучшего альбома
     */
    @Override
    public int compareTo(MusicBand band) {
        return getSales().compareTo(band.getSales());
    }

    /**
     * Возвращает строковое представление объекта MusicBand в формате JSON
     *
     * @return строковое представление объекта MusicBand
     */
    @Override
    public String toString() {
        return "MusicBand{\"id\": " + id + ", " +
                "\"name\": \"" + name + "\", " +
                "\"coordinates\": \"" + coordinates + "\", " +
                "\"creationDate\": \"" + creationDate + "\", " +
                "\"numberOfParticipants\": \"" + numberOfParticipants + "\", " +
                "\"singlesCount\": \"" + singlesCount + "\", " +
                "\"establishmentDate\": \"" + establishmentDate + "\", " +
                "\"genre\": \"" + genre + "\", " +
                "\"bestAlbum\": " + bestAlbum + "}";

    }

    /**
     * Метод для сравнения объектов музыкальной группы
     *
     * @param o объект для сравнения
     * @return true, если объекты равны, false в противном случае
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MusicBand band = (MusicBand) o;
        return Objects.equals(id, band.id);
    }
}