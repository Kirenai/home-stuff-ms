package me.kirenai.re.nourishment.repository;

import me.kirenai.re.nourishment.entity.Nourishment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface NourishmentSortingRepository extends ReactiveSortingRepository<Nourishment, Long> {

    Flux<Nourishment> findAllBy(Pageable pageable);

}
