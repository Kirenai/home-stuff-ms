package me.kirenai.re.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.user.api.RoleManager;
import me.kirenai.re.user.dto.UserRequest;
import me.kirenai.re.user.dto.UserResponse;
import me.kirenai.re.user.entity.User;
import me.kirenai.re.user.mapper.UserMapper;
import me.kirenai.re.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleManager roleManager;

    public Mono<UserResponse> findOne(Long id) {
        log.info("Invoking UserService.findOne method");
        return this.userRepository.findById(id)
                .switchIfEmpty(Mono.error(IllegalStateException::new))
                .map(this.userMapper::mapOutUserToUserResponse);
    }

    public Mono<me.kirenai.re.security.dto.UserResponse> findByUsername(String username) {
        log.info("Invoking UserService.findByUsername method");
        return this.userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(IllegalStateException::new))
                .map(this.userMapper::mapOutUserToUserResponseSec);
    }

    @Transactional
    public Mono<UserResponse> create(UserRequest userRequest) {
        log.info("Invoking UserService.create method");
        User user = this.userMapper.mapInUserRequestToUser(userRequest);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        Mono<User> userMono = this.userRepository.save(user);
        Mono<Void> createRoleMono = userMono.flatMap(this.roleManager::createRoleUser);
        return createRoleMono.then(userMono.map(this.userMapper::mapOutUserToUserResponse));
    }

    public Mono<UserResponse> update(Long id, UserRequest userRequest) {
        log.info("Invoking UserService.update method");
        return this.userRepository.findById(id)
                .switchIfEmpty(Mono.error(IllegalStateException::new))
                .flatMap(user -> {
                    user.setPassword(this.passwordEncoder.encode(userRequest.getPassword()));
                    user.setFirstName(userRequest.getFirstName());
                    user.setLastName(userRequest.getLastName());
                    user.setAge(userRequest.getAge());
                    return this.userRepository.save(user);
                })
                .map(this.userMapper::mapOutUserToUserResponse);
    }

    public Mono<Void> delete(Long id) {
        log.info("Invoking UserService.delete method");
        return Mono.fromRunnable(() -> this.userRepository.deleteById(id));
    }

}