import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ToJson {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<Student>();
        students.add(new Student("Olal", 21, 4.5));
        students.add(new Student("Lola", 22, 4.1));
        students.add(new Student("Mike", 18, 3.5));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(students);
        try (FileWriter fw = new FileWriter("src/test/resources/students.json")) {
            fw.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
