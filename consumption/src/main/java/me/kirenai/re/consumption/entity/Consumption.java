package me.kirenai.re.consumption.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Consumption
 *
 * @author Kirenai
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "consumptions")
public class Consumption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consumption_id")
    private Long consumptionId;

    @Column(name = "unit")
    private Integer unit;

    @Column(name = "percentage", precision = 3)
    private Integer percentage;

    @Column(name = "nourishment_id", nullable = false)
    private Long nourishment;

    @Column(name = "user_id", nullable = false)
    private Long user;

}
