package me.kirenai.re.consumption.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Consumption
 *
 * @author Kirenai
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "consumptions")
public class Consumption {

    @Id
    @Column("consumption_id")
    private Long consumptionId;
    @Column("unit")
    private Integer unit;
    @Column("percentage")
    private Integer percentage;
    @Column("nourishment_id")
    private Long nourishmentId;
    @Column("user_id")
    private Long userId;

}
