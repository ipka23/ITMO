package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import persistence.entities.PointEntity;

import java.math.BigDecimal;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PointResponse {
    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal r;
    private String status;
    private String currentTime;
    private String executionTime;

    private boolean isValid;

    private String message;

    private ArrayList<PointEntity> points;

    public PointResponse(BigDecimal x, BigDecimal y, BigDecimal r, String status, String currentTime, String executionTime) {
        this.isValid = true;
        this.x = x;
        this.y = y;
        this.r = r;
        this.status = status;
        this.currentTime = currentTime;
        this.executionTime = executionTime;
    }
    public PointResponse(boolean isValid, String message) {
        this.isValid = isValid;
        this.message = message;
    }

    public PointResponse(ArrayList<PointEntity> points) {
        this.isValid = true;
        this.points = points;
    }


}