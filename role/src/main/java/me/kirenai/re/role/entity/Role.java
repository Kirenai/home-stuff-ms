package me.kirenai.re.role.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Map;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role {

    @Id
    private Long roleId;
    private String name;

    public static Role fromRow(Map<String, Object> row) {
        return Role.builder()
                .roleId((Long.parseLong(row.get("r_roleId").toString())))
                .name((String) row.get("r_name"))
                .build();
    }

}