package models;

import utility.Validatable;

import java.util.Map;

public class Address implements Validatable {
    private String street; //Длина строки не должна быть больше 198, Поле не может быть null
    private String zipCode; //Поле не может быть null
    private Location town; //Поле не может быть null

    public String getStreet() {
        return street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public Location getTown() {
        return town;
    }

    public Address(String street, String zipCode, Location town) {
        this.street = street;
        this.zipCode = zipCode;
        this.town = town;
    }

    public Address(String s) {
        try {
            this.street = s.split(" ; ")[0];
            try {
                this.zipCode = s.split(" ; ")[1].equals("null") ? null : s.split(" ; ")[1];
            } catch (Exception e) {return;};

            try {
                this.town = new Location(Float.parseFloat(s.split(" ; ")[2].split(";")[0]), Double.parseDouble(s.split(" ; ")[2].split(";")[1]), Double.parseDouble(s.split(" ; ")[2].split(";")[2]), s.split(" ; ")[2].split(";")[3]);
            } catch (IllegalArgumentException e) {return;};
        } catch (ArrayIndexOutOfBoundsException e) {return;};
    }
//    public Address(Map<String, String> map) {
//        try {
//            try {
//                this.street = map.get("street");
//            } catch (NumberFormatException e) {}
//            try {
//                this.zipCode = map.get("zipCode");
//            } catch (NumberFormatException e) {}
//            try {
//                this.town = map.get("town")
//            } catch (NumberFormatException e) {}
//        } catch (ArrayIndexOutOfBoundsException e) {}
//    }

    @Override
    public boolean validate() {
        if (street == null || street.length() > 198) return false;
        if (zipCode == null) return false;
        if (town == null) return false;
        return true;
    }
    @Override
    public String toString() {
//        return "{" + "\"street\": " + street + ", \"zipCode\": " + zipCode + ", \"town\": " + town + "}";
        return street + " ; " + zipCode + " ; " + town;
    }
}