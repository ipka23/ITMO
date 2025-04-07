package common_entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class Coordinates {
    private Integer x; //Поле не может быть null
    private float y; //Максимальное значение поля: 751


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