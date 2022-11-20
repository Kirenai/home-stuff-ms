package me.revilla.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * NourishmentService
 *
 * @author Kirenai
 */
@Slf4j
@Service
@Qualifier("user.service")
public record UserServiceImp(UserRepository userRepository) implements UserService {

    @Override
    public List<User> findAll() {
        return Collections.emptyList();
    }

    @Override
    public User findOne(Long id) {
        return User.builder().userId(id).build();
    }

    @Override
    public User create(User data) {
        return User.builder().build();
    }

    @Override
    public User update(Long id, User data) {
        return User.builder().build();
    }

    @Override
    public void delete(Long id) {
    }

}
