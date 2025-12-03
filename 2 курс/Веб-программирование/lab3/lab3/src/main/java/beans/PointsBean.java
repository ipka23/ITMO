package beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import utility.CoordinatesValidator;
import utility.HitChecker;
import utility.Point;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

@Named("pointsBean")
@SessionScoped
@Getter
@Setter
public class PointsBean implements Serializable {
    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal r;
    private String status;
    private String currentTime;
    private String executionTime;
    private ArrayList<Point> points;

    private static final double SVG_WIDTH = 300;
    private static final double SVG_HEIGHT = 300;
    private static final double SVG_CENTER_X = SVG_WIDTH / 2;
    private static final double SVG_CENTER_Y = SVG_HEIGHT / 2;
    private static final double FORMAT_TO_MILLIS = 1_000_000;

    public PointsBean() {
    }

    @PostConstruct
    public void init() {
//        dbController = ...
//        points = dbController.get...
        this.x = null;
        this.y = null;
        this.r = null;

        if (points == null) {
            points = new ArrayList<>();
        }
    }

    public void addPointFromSvg(BigDecimal cx, BigDecimal cy, BigDecimal cr) {
//        if (x == null && y == null) return;

        this.x = cx;
        this.y = cy;
        this.r = cr;
//        CoordinatesValidator.validate(this.x, this.y, this.r);
        addPoint();
    }

    public void addPoint() {
        double startTime = System.nanoTime();
        status = HitChecker.check(x, y, r) ? "Попадание" : "Промах";
        currentTime = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(Calendar.getInstance().getTime());
        double endTime = System.nanoTime();
        double t = (endTime - startTime) / FORMAT_TO_MILLIS;
        executionTime = String.format("%.2fms", t);
        Point p = new Point(x, y, r, status, currentTime, executionTime);
        points.add(p);
        System.out.println("Point was added:\n" + p);

    }

    public double getSvgX(Point point) {
        double rPxSize = SVG_WIDTH / 3.0;
        double scale = rPxSize / point.getR().doubleValue();
        return SVG_CENTER_X + point.getX().doubleValue() * scale;
    }

    public double getSvgY(Point point) {
        double rPxSize = SVG_WIDTH / 3.0;
        double scale = rPxSize / point.getR().doubleValue();
        return SVG_CENTER_Y - point.getY().doubleValue() * scale;
    }
    public void resetForm() {
        this.x = null;
        this.y = null;
        this.r = null;
    }
    public String getPointColor(Point p) {
        if (r != null) {
            return HitChecker.check(p.getX(), p.getY(), r) ? "green" : "red";
        } else
            return "black";
    }
}
