package models;

import utility.Validatable;

public class Location implements Validatable {
    private Float x; //Поле не может быть null
    private double y;
    private double z;
    private String name; //Поле может быть null


    public String getName() {
        return name;
    }

    public double getZ() {
        return z;
    }

    public double getY() {
        return y;
    }

    public Float getX() {
        return x;
    }

    public Location(Float x, double y, double z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    @Override
    public boolean validate() {
        if (x == null) return false;
        if (name == null) return false;
        return true;
    }
    @Override
    public String toString() {
//        return "{\"x\": " + x + ", " + "\"y\":" + y + ", " + "\"z\":" + z + "\"}";
        return x + ";" + y + ";" + z + ";" + name;
    }
}