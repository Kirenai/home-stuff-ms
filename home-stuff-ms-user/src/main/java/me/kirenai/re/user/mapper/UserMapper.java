package me.kirenai.re.user.mapper;

import lombok.RequiredArgsConstructor;
import me.kirenai.re.user.dto.UserRequest;
import me.kirenai.re.user.dto.UserResponse;
import me.kirenai.re.user.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserResponse mapOutUserToUserResponse(User user) {
        return this.modelMapper.map(user, UserResponse.class);
    }

    public User mapOutUserRequestToUser(UserRequest user) {
        return this.modelMapper.map(user, User.class);
    }

}