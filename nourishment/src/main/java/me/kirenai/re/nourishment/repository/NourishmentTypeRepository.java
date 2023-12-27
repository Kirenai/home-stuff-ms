package me.kirenai.re.nourishment.repository;

import me.kirenai.re.nourishment.entity.NourishmentType;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface NourishmentTypeRepository extends ReactiveCrudRepository<NourishmentType, Long> {

    Mono<NourishmentType> findByName(String name);

}
