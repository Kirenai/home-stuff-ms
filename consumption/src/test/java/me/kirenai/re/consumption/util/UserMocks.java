package me.kirenai.re.consumption.util;

import me.kirenai.re.consumption.dto.UserResponse;

public class UserMocks {

    public static UserResponse userResponseMock(Long userId, String username, String firstName, String lastName, Integer age) {
        return UserResponse.builder()
                .userId(userId)
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .age(age)
                .build();
    }

    public static UserResponse getUserResponse() {
        return userResponseMock(1L, "username1", "firstName1", "lastName1", 15);
    }

}
