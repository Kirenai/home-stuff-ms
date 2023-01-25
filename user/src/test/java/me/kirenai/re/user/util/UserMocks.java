package me.kirenai.re.user.util;

import me.kirenai.re.user.dto.UserRequest;
import me.kirenai.re.user.dto.UserResponse;

import java.util.Arrays;
import java.util.List;

public class UserMocks {
    public static List<UserResponse> getUserResponseList() {
        return Arrays.asList(
                getUserResponseMock(1L, "username1", "firstName1", "lastName1", 10),
                getUserResponseMock(2L, "username2", "firstName2", "lastName2", 15),
                getUserResponseMock(3L, "username3", "firstName3", "lastName3", 20)
        );
    }

    public static UserResponse getUserResponseMock(Long userId,
                                                   String username,
                                                   String firstName,
                                                   String lastName,
                                                   Integer age) {
        return UserResponse.builder()
                .userId(userId)
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .age(age)
                .build();
    }

    public static UserResponse getUserResponse() {
        return getUserResponseList().get(0);
    }

    public static UserRequest getUserRequest() {
        return UserRequest.builder()
                .username("username1")
                .password("password1")
                .firstName("firstName1")
                .lastName("lastName")
                .age(15)
                .build();
    }

}
