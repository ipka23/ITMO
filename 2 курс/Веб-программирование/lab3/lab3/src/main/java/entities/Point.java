package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "points")
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "x", nullable = false)
    private BigDecimal x;

    @Column(name = "y", nullable = false)
    private BigDecimal y;

    @Column(name = "r", nullable = false)
    private BigDecimal r;

    @Column(name = "result", nullable = false)
    private String status;

    @Column(name = "date", nullable = false)
    private String currentTime;

    @Column(name = "exectime", nullable = false)
    private String executionTime;

    public Point(BigDecimal x, BigDecimal y, BigDecimal r,
                 String status, String currentTime, String executionTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.status = status;
        this.currentTime = currentTime;
        this.executionTime = executionTime;
    }
}