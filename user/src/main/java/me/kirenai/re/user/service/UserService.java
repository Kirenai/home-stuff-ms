package me.kirenai.re.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.user.api.RoleManager;
import me.kirenai.re.user.dto.UserRequest;
import me.kirenai.re.user.dto.UserResponse;
import me.kirenai.re.user.entity.User;
import me.kirenai.re.user.mapper.UserMapper;
import me.kirenai.re.user.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleManager roleManager;

    public List<UserResponse> findAll(Pageable pageable) {
        log.info("Invoking UserService.findAll method");
        return this.userRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(this.userMapper::mapOutUserToUserResponse)
                .toList();
    }

    public UserResponse findOne(Long id) {
        log.info("Invoking UserService.findOne method");
        User user = this.userRepository.findById(id).orElseThrow();
        return this.userMapper.mapOutUserToUserResponse(user);
    }

    public me.kirenai.re.security.dto.UserResponse findByUsername(String username) {
        log.info("Invoking UserService.findByUsername method");
        User user = this.userRepository.findByUsername(username).orElseThrow();
        return this.userMapper.mapOutUserToUserResponseSec(user);
    }

    @Transactional
    public UserResponse create(UserRequest userRequest) {
        log.info("Invoking UserService.create method");
        User user = this.userMapper.mapInUserRequestToUser(userRequest);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);
        this.roleManager.createRoleUser(user);
        return this.userMapper.mapOutUserToUserResponse(user);
    }

    public UserResponse update(Long id, UserRequest userRequest) {
        log.info("Invoking UserService.update method");
        User userFound = this.userRepository.findById(id).orElseThrow();
        userFound.setPassword(this.passwordEncoder.encode(userRequest.getPassword()));
        userFound.setFirstName(userRequest.getFirstName());
        userFound.setLastName(userRequest.getLastName());
        userFound.setAge(userRequest.getAge());
        this.userRepository.save(userFound);
        return this.userMapper.mapOutUserToUserResponse(userFound);
    }

    public void delete(Long id) {
        log.info("Invoking UserService.delete method");
        this.userRepository.deleteById(id);
    }

}