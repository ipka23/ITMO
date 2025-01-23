package models;

import utility.Validatable;

public class Coordinates implements Validatable {
    private int x;
    private float y;

    public Coordinates(int x, float y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates(String s) {
        try {
            try { this.x = Integer.parseInt(s.split(";")[0]); } catch (NumberFormatException e) { }
            try { this.y = (float) Double.parseDouble(s.split(";")[1]); }catch (NumberFormatException e) { }
        } catch (ArrayIndexOutOfBoundsException e) {}
    }

    @Override
    public String toString() {
        return x + ";" + y;
    }

    @Override
    public boolean validate() {
        return true;
    }
}