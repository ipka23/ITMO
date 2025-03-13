package entities;

import utility.interfaces.Validatable;

/**
 * Данный класс представляет координаты и реализует интерфейс Validatable для проверки корректности данных;
 * Содержит информацию о координатах x и y
 *
 * @author ipka23
 */
public class Coordinates implements Validatable {
    private Integer x; //Поле не может быть null
    private float y; //Максимальное значение поля: 751

    /**
     * @param x - координата x
     * @param y - координата y
     */
    public Coordinates(Integer x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Метод для проверки валидности координат
     *
     * @return true, если координаты валидны, false в противном случае
     */
    @Override
    public boolean isValid() {
        if (x == null) return false;
        if (y > 751) return false;
        return true;
    }

    /**
     * Возвращает строковое представление объекта координат в формате "x;y"
     *
     * @return строковое представление объекта координат
     */
    @Override
    public String toString() {
        return x + ";" + y;
    }

}