package me.kirenai.re.nourishment.repository;

import me.kirenai.re.nourishment.entity.Nourishment;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface NourishmentRepository extends ReactiveCrudRepository<Nourishment, Long> {

    Flux<Nourishment> findByIsAvailable(Boolean isAvailable);

    Flux<Nourishment> findByUserId(Long userId);

}