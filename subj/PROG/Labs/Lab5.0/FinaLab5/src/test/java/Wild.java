import entities.MusicBand;

import java.util.ArrayList;
import java.util.List;

public class Wild {
    private static List<Object> obj = new ArrayList<>();
    private static List<String> strs = new ArrayList<>();
    private static List<Integer> ints = new ArrayList<>();
    private static MusicBand band1 = new MusicBand("Band1");
    private static MusicBand band2 = new MusicBand("Band1");
    private static MusicBand band3 = new MusicBand("Band1");
    public static Integer integer = new Integer(1);
    public static void main(String[] args) {

        obj.add(band1);
        obj.add(band2);
        obj.add(band3);
        printItems(obj);

        strs.add("s1");
        strs.add("s2");
        strs.add("s3");
        printItems(strs);
        System.out.println(integer);

        ints.add(1);
        ints.add(2);
        ints.add(3);
        printItems(ints);

    }

    public static void printItems(List<?> list) {
        for (Object item : list) {
            System.out.println(item);
        }
    }

}
