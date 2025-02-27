package models;

import utility.Validatable;

public class Location implements Validatable {
    private Float x; //Поле не может быть null
    private double y;
    private double z;
    private String name; //Поле может быть null

    public Location(Float x, double y, double z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    public boolean isValid() {
        if (x == null) return false;
        if (name == null) return false;
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return "address{\"(x,y,z)\": (" + x + ", " + y + ", " + z  + ")" + ", " +
                "\"name\": \"" + name + "\"}";
    }
}