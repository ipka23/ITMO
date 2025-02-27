import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class FromJson {
    public static void main(String[] args) {
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        try (FileReader reader = new FileReader("src/test/resources/file.json")){
            List<Student> students = gson.fromJson(reader, new TypeToken<List<Student>>(){}.getType());
            for (Student student : students) {
                System.out.println(student.name + " " + student.age + " " + student.grades);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
