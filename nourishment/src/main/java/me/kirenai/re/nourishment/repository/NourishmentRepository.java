package me.kirenai.re.nourishment.repository;

import me.kirenai.re.nourishment.entity.Nourishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NourishmentRepository extends JpaRepository<Nourishment, Long> {

    List<Nourishment> findByIsAvailable(Boolean isAvailable);

    List<Nourishment> findByUserId(Long userId);

}