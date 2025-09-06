package common_entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Данный класс представляет координаты и реализует интерфейс Validatable для проверки корректности данных;
 * Содержит информацию о координатах x и y
 *
 * @author ipka23
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Coordinates implements Serializable {
    @Serial
    private static final long serialVersionUID = 8799597549969589219L;
    private Integer x; //Поле не может быть null, Максимальное значение поля: 1000, Минимальное значение поля: -1000
    private float y; //Максимальное значение поля: 1000, Минимальное значение поля: -1000


    /**
     * Возвращает строковое представление объекта координат в формате "x;y"
     *
     * @return строковое представление объекта координат
     */
    @Override
    public String toString() {
        return x + ";" + y;
    }

    public boolean validate() {
        if (x == null || x < -1000 || x > 1000) return false;
        if (y < -1000 || y > 1000) return false;
        return true;
    }
}