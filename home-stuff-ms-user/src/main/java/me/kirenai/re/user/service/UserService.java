package me.kirenai.re.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.user.entity.User;
import me.kirenai.re.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAll() {
        return Collections.emptyList();
    }

    public User findOne(Long id) {
        return User.builder().userId(id).build();
    }

    public User create(User data) {
        return User.builder().build();
    }

    public User update(Long id, User data) {
        return User.builder().build();
    }

    public void delete(Long id) {
    }

}
