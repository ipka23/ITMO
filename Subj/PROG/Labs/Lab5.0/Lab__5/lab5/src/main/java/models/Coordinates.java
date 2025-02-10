package models;

import utility.Validatable;

public class Coordinates implements Validatable {
    private Integer x; //Поле не может быть null
    private float y; //Максимальное значение поля: 751

    public Coordinates(Integer x, float y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates(String s) {
        try {
            try { this.x = Integer.parseInt(s.split(";")[0]); } catch (NumberFormatException e) { }
            try { this.y = Float.parseFloat(s.split(";")[1]); } catch (NumberFormatException e) { }
        } catch (ArrayIndexOutOfBoundsException e) {}
    }
    @Override
    public boolean isValid() {
        if (x == null) return false;
        if (y > 751) return false;
        return true;
    }

    @Override
    public String toString() {
        return x + ";" + y;
    }
}