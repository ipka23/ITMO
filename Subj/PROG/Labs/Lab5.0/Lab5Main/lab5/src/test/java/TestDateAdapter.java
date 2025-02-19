import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

import java.util.Date;

public class TestDateAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {
}
