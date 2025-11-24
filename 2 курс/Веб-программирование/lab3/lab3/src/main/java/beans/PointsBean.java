package beans;

import lombok.Getter;
import lombok.Setter;
import utility.Point;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ManagedBean
@SessionScoped
@Getter
@Setter
public class PointsBean {
    private String x;
    private String y;
    private String r;
    private String status;
    private String currentTime;
    private String executionTime;
    private ArrayList<Point> points = new ArrayList<>();

    private static final int SVG_WIDTH = 300;
    private static final int SVG_HEIGHT = 300;
    private static final int SVG_CENTER_X = SVG_WIDTH / 2;
    private static final int SVG_CENTER_Y = SVG_HEIGHT / 2;

    public PointsBean() {
    }

    public void addPoint(Point p) {
        points.add(p);
    }

    public double getSvgX(Point point) {
        double rPxSize = SVG_WIDTH / 3.0;
        double scale = rPxSize / Double.parseDouble(point.getR());
        return SVG_CENTER_X + Double.parseDouble(point.getX()) * scale;
    }

    public double getSvgY(Point point) {
        double rPxSize = SVG_WIDTH / 3.0;
        double scale = rPxSize / Double.parseDouble(point.getR());
        return SVG_CENTER_Y - Double.parseDouble(point.getY()) * scale;
    }
}
