import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Aboba extends Element implements Validatable, Serializable {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Coordinates coordinates; //Поле не может быть null
    private WeaponType weaponType; //Поле может быть null

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
}