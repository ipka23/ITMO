package models;

import utility.Validatable;

public class Coordinates implements Validatable {
    private double x; //Значение поля должно быть больше -80
    private Float y; //Поле не может быть null

    public Coordinates(double x, Float y) {
        this.x = x;
        this.y = y;
    }

    public boolean validate() {
        if (x <= -80) return false;
        return true;
    }
}