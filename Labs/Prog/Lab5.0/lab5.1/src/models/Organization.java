package models;

import utility.Element;
import utility.Validatable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


public class Organization extends Element implements Validatable {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private double annualTurnover; //Значение поля должно быть больше 0
    private OrganizationType organizationType; //Поле не может быть null
    private Address address; //Поле может быть null

    public Organization(Long id, String name, Coordinates coordinates, double annualTurnover, Date creationDate, OrganizationType organizationType, Address address) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.annualTurnover = annualTurnover;
        this.creationDate = creationDate;
        this.organizationType = organizationType;
        this.address = address;
    }

    public Organization(Long id, String name, Coordinates coordinates, double annualTurnover, OrganizationType organizationType, Address postalAddress) {
        this(id, name, coordinates, annualTurnover,  new Date(), organizationType, postalAddress);
    }

    public Organization(Long id, String name, Coordinates coordinates, OrganizationType organizationType, double annualTurnover, Address address) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.organizationType = organizationType;
        this.annualTurnover = annualTurnover;
        this.address = address;
    }

    public Organization(Long id, String name, Coordinates coordinates, Double organizationType, Object annualTurnover, Object address) {
        super();
    }

    public static String[] toArray(Organization e) {
        var list = new ArrayList<String>();
        list.add(e.getId().toString());
        list.add(e.getName());
        list.add(e.getCoordinates().toString());
        list.add(e.getCreationDate().toString());
        list.add(e.getOrganizationType().toString());
        list.add(String.valueOf(e.annualTurnover));
        list.add(e.getAddress().toString());
        return list.toArray(new String[0]);
    }

    public static Organization fromArray(String[] a) {
        Long id;
        String name;
        Coordinates coordinates;
        Date creationDate = null;
        double annualTurnover = 0.0;
        OrganizationType organizationType = null;
        Address address = null;
        if (a.length < 7) {
            System.err.println("Ошибка: Недостаточно данных для создания Organization.");
            return null;
        }

        try {

            try {
                id = Long.parseLong(a[0]);
            } catch (NumberFormatException e) {
                id = null;
            }

            name = a[1];
            coordinates = new Coordinates(a[2]);

            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                creationDate = dateFormat.parse(a[3]);
            } catch (ParseException e) {
                System.err.println("Ошибка: Неверный формат даты.");
            }

            try {
                annualTurnover = Double.parseDouble(a[4]);
            }
            catch (NumberFormatException e) {}

            try {
                organizationType = OrganizationType.valueOf(a[5]);
            }
            catch (IllegalArgumentException e) {}

            try {
                address = new Address(a[6]);
            } catch (Exception e) {}
            return new Organization(id, name, coordinates, annualTurnover, creationDate, organizationType, address);

        } catch (ArrayIndexOutOfBoundsException e) {}
        return null;
    }


    @Override
    public String toString() {
        return "organization{\"id\": " + id + ", " +
                "\"name\": \"" + name + "\", " +
                "\"creationDate\": \"" + creationDate + "\", " +
                "\"coordinates\": \"" + coordinates + "\", " +
                "\"organizationType\": " + (organizationType == null ? "null" : "\""+organizationType+"\"") + ", " +
                "\"annualTurnover\": \"" + annualTurnover + "\", " +
                "\"address\": \"" + "{" + address + "}" + "}";
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


    public Address getAddress() {
        return address;
    }

    public OrganizationType getOrganizationType() {
        return organizationType;
    }

    public double getAnnualTurnover() {
        return annualTurnover;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, creationDate, coordinates, organizationType);
    }
}