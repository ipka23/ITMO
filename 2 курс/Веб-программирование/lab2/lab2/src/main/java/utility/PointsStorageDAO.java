package utility;

import java.util.*;

public class PointsStorageDAO {
    private static Set<Point> points = Collections.synchronizedSet(new LinkedHashSet<>());

    public static void add(Point p) {
        points.add(p);
    }

    public static boolean exists(Point p) {
        return points.contains(p);
    }

    public static LinkedList<Point> getPoints() {
        synchronized (points) {
            return new LinkedList<>(points);
        }
    }
}
