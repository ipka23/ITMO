import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        Person Ilya = new Person(18, "Ilya", Person.Sex.MALE);
        Person Semion = new Person(18, "Semion", Person.Sex.MALE);
        System.out.println(Ilya.sex());
        ArrayList<Person> people = new ArrayList<Person>(); //создание массива (ArrayList) объектов класса Person
        people.add(Ilya);
        people.add(0, Semion);
        System.out.println(people);
    }
}
