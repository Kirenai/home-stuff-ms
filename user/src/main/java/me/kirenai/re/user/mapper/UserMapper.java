package me.kirenai.re.user.mapper;

import me.kirenai.re.user.dto.UserRequest;
import me.kirenai.re.user.dto.UserResponse;
import me.kirenai.re.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User mapInUserRequestToUser(UserRequest user);

    UserResponse mapOutUserToUserResponse(User user);

}