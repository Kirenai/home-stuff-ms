package me.kirenai.re.consumption.repository;

import me.kirenai.re.consumption.entity.Consumption;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * ConsumptionRepository
 *
 * @author Kirenai
 */
public interface ConsumptionRepository extends ReactiveCrudRepository<Consumption, Long> {
}
