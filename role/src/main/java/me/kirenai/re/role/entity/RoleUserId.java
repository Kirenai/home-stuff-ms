package me.kirenai.re.role.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class RoleUserId implements Serializable {

    @Column(name = "role_id", nullable = false)
    private Long roleId;
    @Column(name = "user_id", nullable = false)
    private Long userId;

}