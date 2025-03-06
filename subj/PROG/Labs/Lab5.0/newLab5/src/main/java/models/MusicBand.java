package models;

import exceptions.ValidateException;
import utility.Validatable;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

/**
 * Данный класс представляет музыкальную группу и реализует интерфейс Validatable для проверки корректности данных;
 * Содержит информацию о музыкальной группе: id, название, координаты, дату создания, количество участников, количество синглов, дату основания, жанр музыки и лучший альбом
 *
 * @author ipka23
 */
public class MusicBand implements Comparable<MusicBand>, Validatable {


    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private String creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long numberOfParticipants; //Поле может быть null, Значение поля должно быть больше 0
    private Long singlesCount; //Поле не может быть null, Значение поля должно быть больше 0
    private java.util.Date establishmentDate; //Поле может быть null
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
        this.creationDate = LocalDate.now().toString();
        this.numberOfParticipants = numberOfParticipants;
        this.singlesCount = singlesCount;
        this.establishmentDate = establishmentDate;
        this.genre = genre;
        this.bestAlbum = bestAlbum;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * Метод для установки названия музыкальной группы
     *
     * @param name название музыкальной группы
     */
    public void setName(String name) {
        this.name = name;
    }
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public void setNumberOfParticipants(Long numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public void setSinglesCount(Long singlesCount) {
        this.singlesCount = singlesCount;
    }

    public void setEstablishmentDate(Date establishmentDate) {
        this.establishmentDate = establishmentDate;
    }

    public void setMusicGenre(MusicGenre genre) {
        this.genre = genre;
    }

    public void setBestAlbum(Album bestAlbum) {
        this.bestAlbum = bestAlbum;
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
    public MusicBand(long id, String name, Coordinates coordinates, Long numberOfParticipants, Long singlesCount, Date establishmentDate, MusicGenre genre, Album bestAlbum) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.numberOfParticipants = numberOfParticipants;
        this.singlesCount = singlesCount;
        this.establishmentDate = establishmentDate;
        this.genre = genre;
        this.bestAlbum = bestAlbum;
        this.creationDate = LocalDate.now().toString();
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
     * Метод для проверки валидности полей музыкальной группы
     *
     * @return true, если все поля валидны, false в противном случае
     */
    @Override
    public boolean isValid() throws ValidateException {
        if (name == null || name.isEmpty()) return false;
        if (coordinates == null || !coordinates.isValid()) return false;
        if (creationDate == null) return false;
        if (numberOfParticipants == null || numberOfParticipants <= 0) return false;
        if (singlesCount == null || singlesCount <= 0) return false;
        if (establishmentDate == null) return false;
        if (genre == null) return false;
        if (bestAlbum == null || !bestAlbum.isValid()) return false;
        return true;
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


    /**
     * Метод для обновления данных музыкальной группы
     *
     * @param band объект MusicBand с новыми данными
     */
    public void update(MusicBand band) {
        this.name = band.name;
        this.coordinates = band.coordinates;
        this.creationDate = LocalDate.now().toString();
        this.numberOfParticipants = band.numberOfParticipants;
        this.singlesCount = band.singlesCount;
        this.establishmentDate = band.establishmentDate;
        this.genre = band.genre;
        this.bestAlbum = band.bestAlbum;
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

