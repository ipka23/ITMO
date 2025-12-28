package services;

import dto.PointRequest;
import dto.PointResponse;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import persistence.dao.PointDAO;
import persistence.entities.PointEntity;
import utility.exceptions.UserNotFoundException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Stateless
public class PointService {

    @EJB
    private PointDAO db;

    public PointResponse validate(PointRequest request) {
        BigDecimal y;
        if (request.getX() == null || request.getX().isEmpty()) {
            return new PointResponse(false, "X не может быть пустым!");
        } else if (!request.getX().matches("^-2|-1\\.5|-1|-0\\.5|0|0\\.5|1|1\\.5|2$")) {
            return new PointResponse(false, "X должен принадлежать {-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2}");
        }

        if (request.getY() == null || request.getY().isEmpty()) {
            return new PointResponse(false, "Y не может быть пустым!");
        } else y = new BigDecimal(request.getY());
        if (y.compareTo(BigDecimal.valueOf(-3)) < 0 || y.compareTo(BigDecimal.valueOf(5)) > 0) {
            return new PointResponse(false, "X должен принадлежать [-3; 5]!");
        }

        if (request.getR() == null || request.getR().isEmpty()) {
            return new PointResponse(false, "R не может быть пустым!");
        } else if (!request.getR().matches("^0|0\\.5|1|1\\.5|2$")) {
            return new PointResponse(false, "R должен принадлежать {0, 0.5, 1, 1.5, 2}");
        }
        PointResponse response = new PointResponse();
        response.setValid(true);
        return response;
    }


    public PointResponse add(PointEntity point, long startTime, Long userId) {
        if (isHit(point)) {
            point.setStatus("Попадание");
        } else {
            point.setStatus("Промах");
        }
        point.setCurrentTime(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(Calendar.getInstance().getTime()));
        db.addPoint(point, startTime, userId);
        return new PointResponse(point.getX(), point.getY(), point.getR(), point.getStatus(), point.getCurrentTime(), point.getExecutionTime());
    }


    public boolean isHit(PointEntity point) {
        BigDecimal x = point.getX();
        BigDecimal y = point.getY();
        BigDecimal r = point.getR();
        if (x.compareTo(BigDecimal.ZERO) > 0 && y.compareTo(BigDecimal.ZERO) > 0) {
            return false;
        }

        if (x.compareTo(BigDecimal.ZERO) == 0 && y.compareTo(BigDecimal.ZERO) == 0) {
            return true;
        }

        return triangleIsHit(x, y, r) || rectangleIsHit(x, y, r) || circleIsHit(x, y, r);
    }

    private boolean circleIsHit(BigDecimal x, BigDecimal y, BigDecimal r) {
        if (x.compareTo(BigDecimal.ZERO) <= 0 && y.compareTo(BigDecimal.ZERO) <= 0) {
            BigDecimal xSquared = x.pow(2);
            BigDecimal ySquared = y.pow(2);
            BigDecimal sumOfSquares = xSquared.add(ySquared);
            BigDecimal rSquared = r.pow(2);
            return sumOfSquares.compareTo(rSquared) <= 0;
        }
        return false;
    }

    private boolean rectangleIsHit(BigDecimal x, BigDecimal y, BigDecimal r) {
        if (x.compareTo(BigDecimal.ZERO) >= 0 && y.compareTo(BigDecimal.ZERO) <= 0) {

            BigDecimal halfR = r.divide(new BigDecimal("2"), 10, RoundingMode.HALF_UP);
            boolean xIsInRange = x.compareTo(BigDecimal.ZERO) >= 0
                    && x.compareTo(halfR) <= 0;
            boolean yIsInRange = y.compareTo(BigDecimal.ZERO) <= 0
                    && y.compareTo(r.negate()) >= 0;

            return xIsInRange && yIsInRange;
        }
        return false;
    }

    private boolean triangleIsHit(BigDecimal x, BigDecimal y, BigDecimal r) {
        if (x.compareTo(BigDecimal.ZERO) <= 0 && y.compareTo(BigDecimal.ZERO) >= 0) {
            BigDecimal twoX = x.multiply(new BigDecimal("2"));
            BigDecimal yOffset = twoX.add(r);

            return y.compareTo(yOffset) <= 0;
        }
        return false;
    }

    public PointResponse getPointsById(Long userId) {
        try {
            return new PointResponse(db.getPoints(userId));
        } catch (UserNotFoundException e) {
            return new PointResponse(false, e.getMessage());
        }

    }
}
