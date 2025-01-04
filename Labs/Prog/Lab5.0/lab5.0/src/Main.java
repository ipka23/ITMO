import java.util.ArrayList;
import java.util.List;

public class Main
{
    static List<Aboba> abobas = new ArrayList<Aboba>();
    public static void main(String[] args) {
        abobas.add(new Aboba(10, "abobus", new Coordinates(0,2f), WeaponType.HAMMER));
        abobas.add(new Aboba(11, "abobus2", new Coordinates(1,2f), null));

        for (var e: abobas) System.out.println(e);
    }
}