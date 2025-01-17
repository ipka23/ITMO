package models;

import utility.Element;
import utility.Validatable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Objects;

public class Aboba extends Element implements Validatable, Serializable {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Coordinates coordinates; //Поле не может быть null
    private WeaponType weaponType; //Поле может быть null
    private static int idCounter = 1;
    public Aboba(int id, String name, LocalDateTime creationDate, Coordinates coordinates, WeaponType weaponType) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.coordinates = coordinates;
        this.weaponType = weaponType;
    }

    public Aboba(int id, String name, Coordinates coordinates, WeaponType weaponType) {
        this(id, name, LocalDateTime.now(), coordinates, weaponType);
    }




    public boolean validate() {
        if (id <= 0) return false;
        if (name == null || name.isEmpty()) return false;
        if (creationDate == null) return false;
        if (coordinates == null || !coordinates.validate()) return false;
        return true;
    }


    public static Aboba fromArray(String[] a) {
        Long id;
        String name;
        LocalDateTime creationDate;
        Coordinates coordinates;
        WeaponType weaponType;
        try {
            try {
                id = Long.parseLong(a[0]);
            } catch (NumberFormatException e) { id = null; }
            name = a[1];
            try {
                creationDate = LocalDateTime.parse(a[2], DateTimeFormatter.ISO_DATE_TIME);
            } catch (DateTimeParseException e) { creationDate = null; };
            coordinates = new Coordinates(a[3]);
            try {
                weaponType = a[4].equals("null") ? null : WeaponType.valueOf(a[4]);
            } catch (NullPointerException | IllegalArgumentException  e) { weaponType = null; }
            return new Aboba(Math.toIntExact(id), name, creationDate, coordinates, weaponType);
        } catch (ArrayIndexOutOfBoundsException e) {}
        return null;
    }

    public static String[] toArray(Aboba e) {
        var list = new ArrayList<String>();
        list.add(String.valueOf(e.getId()));
        list.add(e.getName());
        list.add(e.getCreationDate().format(DateTimeFormatter.ISO_DATE_TIME));
        list.add(e.getCoordinates().toString());
        list.add(e.getWeaponType() == null ? "null" : e.getWeaponType().toString());
        return list.toArray(new String[0]);
    }



    @Override
    public String toString() {
        return "aboba{\"id\": " + id + ", " +
                "\"name\": \"" + name + "\", " +
                "\"creationDate\": \"" + creationDate.format(DateTimeFormatter.ISO_DATE_TIME) + "\", " +
                "\"coordinates\": \"" + coordinates + "\", " +
                "\"weaponType\": " + (weaponType == null ? "null" : "\""+weaponType+"\"") + "}";
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int compareTo(Element element) {
        return (int)(this.id - element.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aboba that = (Aboba) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, creationDate, coordinates, weaponType);
    }


    public String getName() {
        return name;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public static int getIdCounter() {
        return idCounter;
    }
}