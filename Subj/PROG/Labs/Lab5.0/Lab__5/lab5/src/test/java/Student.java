import java.util.List;

public class Student {
    String name;
    int age;
    Double gpa;
    List<Integer> grades;
    public Student(String name, int age, List<Integer> grades) {
        this.name = name;
        this.age = age;
        this.grades = grades;
    }

    public Student(String name, int age, Double gpa) {
        this.name = name;
        this.age = age;
        this.gpa = gpa;
    }
}
