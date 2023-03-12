package me.kirenai.re.role.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles_users")
public class RoleUser {

    @EmbeddedId
    private RoleUserId id;

    @ManyToOne
    @MapsId("role_id")
    @JoinColumn(name = "role_id")
    private Role role;

    @Transient
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private Long userId;

}