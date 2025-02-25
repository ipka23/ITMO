package utility;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");


    @Override
    public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        try {
            return dateFormat.parse(jsonElement.getAsString());
        } catch (ParseException e) {
            throw new JsonParseException(e);
        }
    }

    @Override
    public JsonElement serialize(Date date, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(dateFormat.format(date));
    }
}
