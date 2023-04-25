package me.kirenai.re.role.repository;

import me.kirenai.re.role.entity.RoleUser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RoleUserRepository {

    Flux<RoleUser> findByUserId(Long userId);

    Mono<RoleUser> saveRoleUser(RoleUser roleUser);

}