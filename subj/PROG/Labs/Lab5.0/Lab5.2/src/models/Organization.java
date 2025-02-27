package models;

import utility.Element;
import utility.Validatable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Organization extends Element implements Validatable {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private double annualTurnover; //Значение поля должно быть больше 0
    private OrganizationType type; //Поле не может быть null
    private Address address; //Поле может быть null

    public Organization(String name, Coordinates coordinates, double annualTurnover, OrganizationType type, Address postalAddress) {
        this.name = name;
        this.coordinates = coordinates;
        this.annualTurnover = annualTurnover;
        this.type = type;
        this.address = postalAddress;
    }

    public Organization(String name, Coordinates coordinates, double annualTurnover, OrganizationType type) {
        this.name = name;
        this.coordinates = coordinates;
        this.annualTurnover = annualTurnover;
        this.type = type;
    }

    public Organization(Long id, String name, Coordinates coordinates, double annualTurnover, Date creationDate, OrganizationType type, Address address) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.annualTurnover = annualTurnover;
        this.creationDate = creationDate;
        this.type = type;
        this.address = address;
    }

    public boolean isValid() {
        if (id <= 0) return false;
        if (name == null || name.isEmpty()) return false;
        if (creationDate == null) return false;
        if (coordinates == null || !coordinates.isValid()) return false;
        if (annualTurnover <= 0) return false;
        if (type == null) return false;
        if (address == null || !address.isValid()) return false;
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

    public static String[] toArray(Organization e) {
        var list = new ArrayList<String>();
        list.add(e.getId().toString());
        list.add(e.getName());
        list.add(e.getCoordinates().toString());
        list.add(e.getCreationDate().toString());
        list.add(e.getType().toString());
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



    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public double getAnnualTurnover() {
        return annualTurnover;
    }

    public void setAnnualTurnover(double annualTurnover) {
        this.annualTurnover = annualTurnover;
    }

    public OrganizationType getType() {
        return type;
    }

    public void setType(OrganizationType type) {
        this.type = type;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "organization{\"id\": " + id + ", " +
                "\"name\": \"" + name + "\", " +
                "\"coordinates\": \"" + coordinates + "\", " +
                "\"creationDate\": \"" + creationDate + "\", " +
                "\"annualTurnover\": \"" + annualTurnover + "\", " +
                "\"organizationType\": " + (type == null ? "null" : "\""+type+"\"") + ", " +
                "\"postalAddress\": \"" + address  + "\"}";
    }
}