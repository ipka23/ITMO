package models;

import utility.Validatable;

public class Album implements Validatable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Long tracks; //Поле не может быть null, Значение поля должно быть больше 0
    private long length; //Значение поля должно быть больше 0
    private Double sales; //Поле не может быть null, Значение поля должно быть больше 0

    public Album(String name, Long tracks, long length, Double sales) {
        this.name = name;
        this.tracks = tracks;
        this.length = length;
        this.sales = sales;
    }

    @Override
    public boolean isValid() {
        if (name == null || name.isEmpty()) return false;
        if (tracks == null || tracks <= 0) return false;
        if (length <= 0) return false;
        if (sales == null || sales <= 0) return false;
        return true;
    }
}