package entities;

import utility.interfaces.Validatable;

/**
 * Данный класс представляет музыкальный альбом и реализует интерфейс Validatable для проверки корректности данных;
 * Содержит информацию о названии альбома, количестве треков, длине альбома и количестве продаж
 *
 * @author ipka23
 */
public class Album implements Validatable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Long tracks; //Поле не может быть null, Значение поля должно быть больше 0
    private long length; //Значение поля должно быть больше 0
    private Double sales; //Поле не может быть null, Значение поля должно быть больше 0

    /**
     * Конструктор
     *
     * @param name   Название альбома
     * @param tracks Количество треков в альбоме
     * @param length Длина альбома
     * @param sales  Объемы продаж альбома
     */
    public Album(String name, Long tracks, long length, Double sales) {
        this.name = name;
        this.tracks = tracks;
        this.length = length;
        this.sales = sales;
    }

    /**
     * Метод для возвращения количества продаж альбома
     *
     * @return количество продаж альбома
     */
    public Double getSales() {
        return sales;
    }

    /**
     * Метод для проверки валидности полей альбома
     *
     * @return количество продаж альбома
     */
    @Override
    public boolean isValid() {
        if (name == null || name.isEmpty()) return false;
        if (tracks == null || tracks <= 0) return false;
        if (length <= 0) return false;
        if (sales == null || sales <= 0) return false;
        return true;
    }

    /**
     * Возвращает строковое представление объекта альбома в формате JSON
     *
     * @return строковое представление объекта альбома
     */
    @Override
    public String toString() {
        return "{" +
                "\"name\": \"" + name + "\", " +
                "\"tracks\": \"" + tracks + "\", " +
                "\"length\": \"" + length + "\", " +
                "\"sales\": \"" + sales + "\"" +
                "}";
    }
}