import java.time.LocalDateTime;
import java.util.Date;


public class Organization extends Element implements Validatable{
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private double annualTurnover; //Значение поля должно быть больше 0
    private OrganizationType organizationType; //Поле не может быть null
    private Address postalAddress; //Поле может быть null

    public Organization(Long id, String name, Coordinates coordinates, double annualTurnover, Date creationDate, OrganizationType organizationType, Address postalAddress) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.annualTurnover = annualTurnover;
        this.creationDate = creationDate;
        this.organizationType = organizationType;
        this.postalAddress = postalAddress;
    }

    public Organization(Long id, String name, Coordinates coordinates, double annualTurnover, OrganizationType organizationType, Address postalAddress) {
        this(id, name, coordinates, annualTurnover,  new Date(), organizationType, postalAddress);
    }


    @Override
    public String toString() {
        return "organization{\"id\": " + id + ", " +
                "\"name\": \"" + name + "\", " +
                "\"creationDate\": \"" + creationDate + "\", " +
                "\"coordinates\": \"" + coordinates + "\", " +
                "\"organizationType\": " + (organizationType == null ? "null" : "\""+organizationType+"\"") + "}";
    }


    @Override
    public boolean validate() {
        if (id <= 0) return false;
        if (name == null || name.isEmpty()) return false;
        if (creationDate == null) return false;
        if (coordinates == null || !coordinates.validate()) return false;
        return true;
    }


    @Override
    public Long getId() {
        return id;
    }


    @Override
    public int compareTo(Element element) {
        return (int)(this.id - element.getId());
    }
}