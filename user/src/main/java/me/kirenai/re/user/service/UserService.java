package me.kirenai.re.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.user.dto.UserRequest;
import me.kirenai.re.user.dto.UserResponse;
import me.kirenai.re.user.entity.User;
import me.kirenai.re.user.mapper.UserMapper;
import me.kirenai.re.user.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponse> findAll(Pageable pageable) {
        return this.userRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(this.userMapper::mapOutUserToUserResponse)
                .toList();
    }

    public UserResponse findOne(Long id) {
        User user = this.userRepository.findById(id).orElseThrow();
        return this.userMapper.mapOutUserToUserResponse(user);
    }

    public UserResponse create(UserRequest userRequest) {
        User user = this.userMapper.mapOutUserRequestToUser(userRequest);
        this.userRepository.save(user);
        return this.userMapper.mapOutUserToUserResponse(user);

    }

    public UserResponse update(Long id, UserRequest userRequest) {
        User userFound = this.userRepository.findById(id).orElseThrow();
        userFound.setPassword(userRequest.getPassword());
        userFound.setFirstName(userRequest.getFirstName());
        userFound.setLastName(userRequest.getLastName());
        userFound.setAge(userRequest.getAge());
        this.userRepository.save(userFound);
        return this.userMapper.mapOutUserToUserResponse(userFound);
    }

    public void delete(Long id) {
        this.userRepository.deleteById(id);
    }

}