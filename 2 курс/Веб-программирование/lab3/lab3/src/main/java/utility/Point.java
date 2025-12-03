package utility;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;


@ToString
@Getter
@NoArgsConstructor
public class Point {
    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal r;
    private String status;
    private String currentTime;
    private String executionTime;
    @Setter
    private boolean valid;
    @Setter
    private String validationMessage;

    public Point(BigDecimal x, BigDecimal y, BigDecimal r, String status, String currentTime, String executionTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.status = status;
        this.currentTime = currentTime;
        this.executionTime = executionTime;
    }
}

