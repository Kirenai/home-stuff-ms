package me.kirenai.re.nourishment.entity;

import jakarta.persistence.*;
import lombok.*;
import me.kirenai.re.security.model.Auditable;

/**
 * Nourishment
 *
 * @author Kirenai
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "nourishments", uniqueConstraints = {
        @UniqueConstraint(name = "unq_name", columnNames = {"name"})
})
public class Nourishment extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nourishment_id")
    private Long nourishmentId;

    @Column(name = "name", nullable = false, length = 35)
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(columnDefinition = "TEXT", name = "description")
    private String description;

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable;

    @Column(name = "unit")
    private Integer unit;

    @Column(name = "percentage", precision = 3)
    private Integer percentage;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

}