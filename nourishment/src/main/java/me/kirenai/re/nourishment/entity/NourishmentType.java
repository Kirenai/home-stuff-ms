package me.kirenai.re.nourishment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Table("nourishment_type")
public class NourishmentType {

    @Id
    @Column("nourishment_type_id")
    private Long nourishmentTypeId;
    @Column("name")
    private String name;

}
