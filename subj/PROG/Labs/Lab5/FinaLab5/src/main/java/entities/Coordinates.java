package entities;


/**
 * Данный класс представляет координаты и реализует интерфейс Validatable для проверки корректности данных;
 * Содержит информацию о координатах x и y
 *
 * @author ipka23
 */
public class Coordinates {
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
     * Возвращает строковое представление объекта координат в формате "x;y"
     *
     * @return строковое представление объекта координат
     */
    @Override
    public String toString() {
        return x + ";" + y;
    }

}