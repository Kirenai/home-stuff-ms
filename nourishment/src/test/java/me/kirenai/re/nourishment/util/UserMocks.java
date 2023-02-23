package me.kirenai.re.nourishment.util;

import me.kirenai.re.nourishment.dto.UserResponse;
import org.springframework.http.ResponseEntity;

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
        return userResponseMock(1L, "username1", "firstName1", "lastName1", 1);
    }

    public static ResponseEntity<UserResponse> getUserResponseEntity() {
        return ResponseEntity.ok(getUserResponse());
    }

}