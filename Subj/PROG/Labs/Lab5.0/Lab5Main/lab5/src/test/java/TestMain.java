import com.google.gson.JsonPrimitive;
import commands.Add;
import managers.CollectionManager;
import managers.CommandManager;
import managers.DumpManager;
import utility.Console;
import utility.StandartConsole;

public class TestMain {
    public static void main(String[] args) {
        // Создание JsonPrimitive объектов для различных типов данных
        JsonPrimitive intPrimitive = new JsonPrimitive(123);
        JsonPrimitive stringPrimitive = new JsonPrimitive("Hello, World!");
        JsonPrimitive booleanPrimitive = new JsonPrimitive(true);
        JsonPrimitive charPrimitive = new JsonPrimitive('A');

        // Вывод значений JsonPrimitive объектов
        System.out.println("Integer: " + intPrimitive.getAsInt());
        System.out.println("String: " + stringPrimitive.getAsString());
        System.out.println("Boolean: " + booleanPrimitive.getAsBoolean());
        System.out.println("Character: " + charPrimitive.getAsCharacter());
    }
}
