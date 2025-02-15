package utility;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter implements JsonDeserializer<LocalDate>, JsonSerializer<LocalDate> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @Override
    public com.google.gson.JsonElement serialize(LocalDate date, Type typeOfSrc, com.google.gson.JsonSerializationContext context) {
        return new com.google.gson.JsonPrimitive(date.format(formatter));
    }

    @Override
    public LocalDate deserialize(com.google.gson.JsonElement json, Type typeOfT, com.google.gson.JsonDeserializationContext context) {
        return LocalDate.parse(json.getAsString(), formatter);
    }
}
