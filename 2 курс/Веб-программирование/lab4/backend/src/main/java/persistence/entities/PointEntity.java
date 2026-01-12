package persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "points")
public class
PointEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "userId", nullable = false)
    private Long userId;

    public PointEntity(BigDecimal x, BigDecimal y, BigDecimal r,
                       String status, String currentTime, String executionTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.status = status;
        this.currentTime = currentTime;
        this.executionTime = executionTime;
    }

    public PointEntity(BigDecimal x, BigDecimal y, BigDecimal r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

}