package me.kirenai.re.user.repository;

import me.kirenai.re.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * UserRepository
 *
 * @author Kirenai
 */
@Repository
public interface UserSortingRepository extends ReactiveSortingRepository<User, Long> {

    Flux<User> findAllBy(Pageable pageable);

}
