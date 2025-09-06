package common_entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Данный класс представляет музыкальный альбом и реализует интерфейс Validatable для проверки корректности данных;
 * Содержит информацию о названии альбома, количестве треков, длине альбома и количестве продаж
 *
 * @author ipka23
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Album implements Serializable {
    @Serial
    private static final long serialVersionUID = 5626076749192873217L;
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Long tracks; //Поле не может быть null, Значение поля должно быть больше 0
    private long length; //Значение поля должно быть больше 0
    private Double sales; //Поле не может быть null, Значение поля должно быть больше 0

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

    public boolean valdate() {
        if (name == null || name.isEmpty()) return false;
        if (tracks == null || tracks < 0) return false;
        if (length < 0) return false;
        if (sales == null || sales < 0) return false;
        return true;
    }
}