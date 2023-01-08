package me.kirenai.re.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.user.dto.UserRequest;
import me.kirenai.re.user.dto.UserResponse;
import me.kirenai.re.user.entity.User;
import me.kirenai.re.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public List<UserResponse> findAll(Pageable pageable) {
        return this.userRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(it -> this.modelMapper.map(it, UserResponse.class))
                .toList();
    }

    public UserResponse findOne(Long id) {
        User user = this.userRepository.findById(id).orElseThrow();
        return this.modelMapper.map(user, UserResponse.class);
    }

    public UserResponse create(UserRequest userRequest) {
        User user = this.modelMapper.map(userRequest, User.class);
        this.userRepository.save(user);
        return this.modelMapper.map(user, UserResponse.class);

    }

    public UserResponse update(Long id, UserRequest userRequest) {
        User userFound = this.userRepository.findById(id).orElseThrow();
        userFound.setPassword(userRequest.getPassword());
        userFound.setFirstName(userRequest.getFirstName());
        userFound.setLastName(userRequest.getLastName());
        userFound.setAge(userRequest.getAge());
        this.userRepository.save(userFound);
        return this.modelMapper.map(userFound, UserResponse.class);
    }

    public void delete(Long id) {
        this.userRepository.deleteById(id);
    }

}
