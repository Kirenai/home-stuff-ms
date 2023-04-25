package me.kirenai.re.role.dao;

import lombok.RequiredArgsConstructor;
import me.kirenai.re.role.entity.RoleUser;
import me.kirenai.re.role.repository.RoleUserRepository;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RoleUserRepositoryDao implements RoleUserRepository {

    private final DatabaseClient client;

    @Override
    public Flux<RoleUser> findByUserId(Long userId) {
        return this.client
                .sql("SELECT r.role_id r_roleId, r.name r_name FROM roles r LEFT JOIN roles_users ru ON r.role_id = ru.role_id WHERE ru.user_id = :userId")
                .bind("userId", userId)
                .fetch()
                .all()
                .bufferUntilChanged(result -> result.get("r_roleId"))
                .flatMap(RoleUser::fromRows);
    }

    @Override
    public Mono<RoleUser> saveRoleUser(RoleUser roleUser) {
        return this.client
                .sql("INSERT INTO roles_users(role_id, user_id) VALUES(:roleId, :userId)")
                .bind("roleId", roleUser.getRoleId())
                .bind("userId", roleUser.getUserId())
                .fetch()
                .first()
                .doOnNext(result -> {
                    roleUser.setRoleId(Long.parseLong(result.get("roleId").toString()));
                    roleUser.setUserId(Long.parseLong(result.get("userId").toString()));
                })
                .thenReturn(roleUser);
    }

}
