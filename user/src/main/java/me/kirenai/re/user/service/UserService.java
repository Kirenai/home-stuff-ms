package me.kirenai.re.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.exception.user.UserNotFoundExceptionFactory;
import me.kirenai.re.user.api.RoleManager;
import me.kirenai.re.user.dto.UserRequest;
import me.kirenai.re.user.dto.UserResponse;
import me.kirenai.re.user.entity.User;
import me.kirenai.re.user.mapper.UserMapper;
import me.kirenai.re.user.repository.UserRepository;
import me.kirenai.re.user.repository.UserSortingRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserSortingRepository userSortingRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleManager roleManager;

    public Flux<UserResponse> findAll(Pageable pageable) {
        log.info("Invoking UserService.findAll method");
        return this.userSortingRepository.findAllBy(pageable)
                .map(this.userMapper::mapOutUserToUserResponse);
    }

    public Mono<UserResponse> findOne(Long id) {
        log.info("Invoking UserService.findOne method");
        return this.userRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundExceptionFactory(String.format("User not found with id: %d", id))))
                .map(this.userMapper::mapOutUserToUserResponse);
    }

    public Mono<UserResponse> findByUsername(String username) {
        log.info("Invoking UserService.findByUsername method");
        return this.userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new UserNotFoundExceptionFactory(String.format("User not found with username: %s", username))))
                .map(this.userMapper::mapOutUserToUserResponse);
    }

    @Transactional
    public Mono<UserResponse> create(UserRequest userRequest) {
        log.info("Invoking UserService.create method");
        User user = this.userMapper.mapInUserRequestToUser(userRequest);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user)
                .flatMap(userSaved -> this.roleManager.createRoleUser(userSaved)
                        .thenReturn(userSaved))
                .map(this.userMapper::mapOutUserToUserResponse);
    }

    public Mono<UserResponse> update(Long id, UserRequest userRequest) {
        log.info("Invoking UserService.update method");
        return this.userRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundExceptionFactory(String.format("User not found with id: %s", id))))
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
        return this.userRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundExceptionFactory(String.format("User not found with id: %s", id))))
                .flatMap(this.userRepository::delete);
    }

}