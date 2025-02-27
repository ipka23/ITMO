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
    public Coordinates(String s) {
        try {
            try { this.x = Integer.parseInt(s.split(";")[0]); } catch (NumberFormatException e) { }
            try { this.y = Float.parseFloat(s.split(";")[1]); } catch (NumberFormatException e) { }
        } catch (ArrayIndexOutOfBoundsException e) {}
    }
    @Override
    public String toString() {
        return x + ";" + y;
    }

}