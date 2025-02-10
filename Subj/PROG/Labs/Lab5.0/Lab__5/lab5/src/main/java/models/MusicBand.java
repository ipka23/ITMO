package models;

import utility.Validatable;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class MusicBand implements Comparable<MusicBand>, Validatable {


    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long numberOfParticipants; //Поле может быть null, Значение поля должно быть больше 0
    private Long singlesCount; //Поле не может быть null, Значение поля должно быть больше 0
    private java.util.Date establishmentDate; //Поле может быть null
    private MusicGenre genre; //Поле не может быть null
    private Album bestAlbum; //Поле не может быть null

    public MusicBand(long id, String name, Coordinates coordinates, LocalDate creationDate, Long numberOfParticipants, Long singlesCount, Date establishmentDate, MusicGenre genre, Album bestAlbum) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.numberOfParticipants = numberOfParticipants;
        this.singlesCount = singlesCount;
        this.establishmentDate = establishmentDate;
        this.genre = genre;
        this.bestAlbum = bestAlbum;
    }

    public MusicBand(String name, Coordinates coordinates, Long numberOfParticipants, Long singlesCount, Date establishmentDate, MusicGenre genre, Album bestAlbum) {
        this.name = name;
        this.coordinates = coordinates;
        this.numberOfParticipants = numberOfParticipants;
        this.singlesCount = singlesCount;
        this.establishmentDate = establishmentDate;
        this.genre = genre;
        this.bestAlbum = bestAlbum;
    }
    public long getId() {
        return id;
    }

    @Override
    public boolean isValid() {
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

    @Override
    public int compareTo(MusicBand mb) {
        return (int) (this.id - mb.getId());
    }

    @Override
    public String toString() {
        return "models.MusicBand{\"id\": " + id + ", " +
                "\"name\": \"" + name + "\", " +
                "\"coordinates\": \"" + coordinates + "\", " +
                "\"creationDate\": \"" + creationDate + "\", " +
                "\"numberOfParticipants\": \"" + numberOfParticipants + "\", " +
                "\"singlesCount\": \"" + singlesCount + "\", " +
                "\"establishmentDate\": \"" + establishmentDate + "\", " +
                "\"genre\": \"" + genre + "\", " +
                "\"bestAlbum\": \"" + bestAlbum + "\"}";

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MusicBand mb = (MusicBand) o;
        return Objects.equals(id, mb.id);
    }
}

