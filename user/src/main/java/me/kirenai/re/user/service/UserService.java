package me.kirenai.re.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.user.dto.RoleResponse;
import me.kirenai.re.user.dto.UserRequest;
import me.kirenai.re.user.dto.UserResponse;
import me.kirenai.re.user.entity.User;
import me.kirenai.re.user.mapper.UserMapper;
import me.kirenai.re.user.repository.UserRepository;
import me.kirenai.re.user.util.RoleUserPredicate;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    public static final String GET_ROLES_URL = "http://localhost:8084/api/v0/roles";
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RestTemplate restTemplate;
    private final RoleUserPredicate roleUserPredicate;

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

    public UserResponse create(UserRequest userRequest) {
        log.info("Invoking UserService.create method");
        User user = this.userMapper.mapInUserRequestToUser(userRequest);
        RoleResponse[] roleResponse = this.restTemplate.getForObject(GET_ROLES_URL, RoleResponse[].class);
        if (Objects.nonNull(roleResponse)) {
            Long roleId = Stream.of(roleResponse)
                    .filter(roleUserPredicate)
                    .map(RoleResponse::getRoleId)
                    .findFirst()
                    .orElseThrow();
            user.setRoleId(roleId);
        }
        this.userRepository.save(user);
        return this.userMapper.mapOutUserToUserResponse(user);
    }

    public UserResponse update(Long id, UserRequest userRequest) {
        log.info("Invoking UserService.update method");
        User userFound = this.userRepository.findById(id).orElseThrow();
        userFound.setPassword(userRequest.getPassword());
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