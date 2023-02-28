package me.kirenai.re.role.repository;

import me.kirenai.re.role.entity.RoleUser;
import me.kirenai.re.role.entity.RoleUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleUserRepository extends JpaRepository<RoleUser, RoleUserId> {

    List<RoleUser> findByIdUserId(Long userId);

}