package models;

import utility.Element;
import utility.Validatable;

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