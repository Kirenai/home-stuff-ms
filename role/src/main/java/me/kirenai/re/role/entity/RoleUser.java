package me.kirenai.re.role.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "roles_users")
public class RoleUser {

    private Long roleId;
    private Long userId;

    @Builder.Default
    private List<Role> roles = new ArrayList<>();

    public static Mono<RoleUser> fromRows(List<Map<String, Object>> rows) {
        return Mono.just(RoleUser.builder()
                .roles(rows.stream()
                        .map(Role::fromRow)
                        .filter(Objects::nonNull)
                        .toList())
                .build());
    }

}