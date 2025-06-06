package common_entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MusicBand implements Comparable<MusicBand>, Serializable {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long numberOfParticipants; //Поле может быть null, Значение поля должно быть больше 0
    private Long singlesCount; //Поле не может быть null, Значение поля должно быть больше 0
    private Date establishmentDate; //Поле может быть null
    private MusicGenre genre; //Поле не может быть null
    private Album bestAlbum; //Поле не может быть null

    public MusicBand(String name, Coordinates coordinates, Long numberOfParticipants, Long singlesCount, Date establishmentDate, MusicGenre musicGenre, Album bestAlbum) {
        this.name = name;
        this.coordinates = coordinates;
        this.numberOfParticipants = numberOfParticipants;
        this.singlesCount = singlesCount;
        this.establishmentDate = establishmentDate;
        this.genre = musicGenre;
        this.bestAlbum = bestAlbum;
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
        return this.bestAlbum.getSales().compareTo(band.getBestAlbum().getSales());
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
        return Objects.equals(name, band.name);
    }
}