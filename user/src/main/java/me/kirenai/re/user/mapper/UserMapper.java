package me.kirenai.re.user.mapper;

import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.user.dto.UserRequest;
import me.kirenai.re.user.dto.UserResponse;
import me.kirenai.re.user.entity.User;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;

@Slf4j
@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public abstract User mapInUserRequestToUser(UserRequest userRequest);

    public abstract UserResponse mapOutUserToUserResponse(User user);

    @BeforeMapping
    public void mapInLog(UserRequest userRequest) {
        log.info("Invoking UserMapper.mapInUserRequestToUser method");
    }

    @BeforeMapping
    public void mapOutLog(User user) {
        log.info("Invoking UserMapper.mapOutUserToUserResponse method");
    }

}