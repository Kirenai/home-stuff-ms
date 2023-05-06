package me.kirenai.re.category.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Categories
 *
 * @author Kirenai
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "categories")
public class Category {

    @Id
    @Column("category_id")
    private Long categoryId;

    @Column("name")
    private String name;

}