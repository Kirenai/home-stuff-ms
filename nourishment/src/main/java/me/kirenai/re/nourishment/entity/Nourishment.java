package me.kirenai.re.nourishment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Nourishment
 *
 * @author Kirenai RE
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "nourishments")
public class Nourishment {

    @Id
    @Column("nourishment_id")
    private Long nourishmentId;
    @Column("name")
    private String name;
    @Column("image_url")
    private String imageUrl;
    @Column("description")
    private String description;
    @Column("is_available")
    private Boolean isAvailable;
    @Column("unit")
    private Integer unit;
    @Column("percentage")
    private Integer percentage;
    @Column("user_id")
    private Long userId;
    @Column("category_id")
    private Long categoryId;
    @Column("nourishment_type_id")
    private Long nourishmentTypeId;

}