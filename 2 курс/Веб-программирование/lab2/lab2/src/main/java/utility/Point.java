package utility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


@AllArgsConstructor
@ToString
@Getter
public class Point {
    private String x;
    private String y;
    private String r;
    private String hit;
    private String currentTime;
    private String executionTime;
}

