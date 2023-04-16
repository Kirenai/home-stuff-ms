package me.kirenai.re.user.repository;

import me.kirenai.re.user.entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

/**
 * UserRepository
 *
 * @author Kirenai
 */
public interface UserRepository extends ReactiveCrudRepository<User, Long> {

    Mono<User> findByUsername(String username);

}