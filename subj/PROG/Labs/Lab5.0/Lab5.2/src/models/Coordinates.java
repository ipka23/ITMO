package models;

import utility.Validatable;

public class Coordinates implements Validatable {
    private double x; //Значение поля должно быть больше -80
    private Float y; //Поле не может быть null

    public Coordinates(double x, Float y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates(String s) {
        try {
            try { this.x = Double.parseDouble(s.split(";")[0]); } catch (NumberFormatException e) { }
            try { this.y = Float.parseFloat(s.split(";")[1]); } catch (NumberFormatException e) { }
        } catch (ArrayIndexOutOfBoundsException e) {}
    }

    public boolean isValid(){
        if (x <= -80) return false;
        if (y == null) return false;
        return true;
    }

    @Override
    public String toString() {
        return x + ";" + y;
    }
}