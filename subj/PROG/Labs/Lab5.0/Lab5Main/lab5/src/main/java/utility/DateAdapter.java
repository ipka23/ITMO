package utility;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Данный класс отвечает за сериализацию и десериализацию объектов типа Date в формат JSON.
 * Он использует SimpleDateFormat для преобразования даты в строку и обратно.
 * Формат даты: "dd.MM.yyyy".
 *
 * @author ipka23
 */
public class DateAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    /**
     *  Метод для десериализации объекта JSON в объект Date
     *
     * @param jsonElement JSON элемент, содержащий дату в виде строки
     * @param type тип объекта
     * @param jsonDeserializationContext контекст десериализации
     * @return объект типа Date
     * @throws JsonParseException если возникает ошибка при разборе даты
     */
    @Override
    public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        try {
            return dateFormat.parse(jsonElement.getAsString());
        } catch (ParseException e) {
            throw new JsonParseException(e);
        }
    }
    /**
     * Метод для сериализации объекта типа Date в объект JSON
     *
     * @param date объект типа Date
     * @param type тип объекта
     * @param jsonSerializationContext контекст сериализации
     * @return объект JSON, содержащий дату в виде строки
     */
    @Override
    public JsonElement serialize(Date date, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(dateFormat.format(date));
    }
}
