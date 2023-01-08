package me.kirenai.re.user.repository;

import me.kirenai.re.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepository
 * @author Kirenai
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
