package me.kirenai.re.user.mapper;

import me.kirenai.re.user.dto.UserRequest;
import me.kirenai.re.user.dto.UserResponse;
import me.kirenai.re.user.entity.User;
import me.kirenai.re.user.util.UserMocks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserMapperTest {

    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    @DisplayName("mapInTest")
    void mapInUserRequestToUserTest() {
        UserRequest input = UserMocks.getUserRequest();
        User request = this.mapper.mapInUserRequestToUser(input);

        assertNotNull(request);
        assertNotNull(request.getUsername());
        assertNotNull(request.getPassword());
        assertNotNull(request.getFirstName());
        assertNotNull(request.getLastName());
        assertNotNull(request.getAge());

        assertEquals(input.getUsername(), request.getUsername());
        assertEquals(input.getPassword(), request.getPassword());
        assertEquals(input.getFirstName(), request.getFirstName());
        assertEquals(input.getLastName(), request.getLastName());
        assertEquals(input.getAge(), request.getAge());
    }


    @Test
    @DisplayName("mapOutTest")
    void mapOutUserToUserResponseTest() {
        User input = UserMocks.getUser();
        UserResponse response = this.mapper.mapOutUserToUserResponse(input);

        assertNotNull(response);
        assertNotNull(response.getUsername());
        assertNotNull(response.getFirstName());
        assertNotNull(response.getLastName());
        assertNotNull(response.getAge());

        assertEquals(input.getUsername(), response.getUsername());
        assertEquals(input.getFirstName(), response.getFirstName());
        assertEquals(input.getLastName(), response.getLastName());
        assertEquals(input.getAge(), response.getAge());
    }

}