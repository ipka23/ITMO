package utility;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@ToString
@Getter
@NoArgsConstructor
public class Point {
    private String x;
    private String y;
    private String r;
    private String status;
    private String currentTime;
    private String executionTime;
    @Setter
    private boolean valid;
    @Setter
    private String validationMessage;

    public Point(String x, String y, String r, String status, String currentTime, String executionTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.status = status;
        this.currentTime = currentTime;
        this.executionTime = executionTime;
    }
}

