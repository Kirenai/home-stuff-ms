package me.kirenai.re.user.util;

import me.kirenai.re.user.dto.RoleResponse;
import me.kirenai.re.user.dto.UserRequest;
import me.kirenai.re.user.dto.UserResponse;
import me.kirenai.re.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

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

    public static Page<User> getUserPage() {
        return new PageImpl<>(Arrays.asList(
                getUserMock(1L, "username1", "password1", "firstName1", "lastName1", 10),
                getUserMock(2L, "username2", "password2", "firstName2", "lastName2", 15),
                getUserMock(3L, "username3", "password3", "firstName3", "lastName3", 20)
        ));
    }

    private static User getUserMock(Long userId, String username, String password, String firstName, String lastName, Integer age) {
        return User.builder()
                .userId(userId)
                .username(username)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .age(age)
                .build();
    }

    public static User getUser() {
        return getUserPage().getContent().get(0);
    }

    public static ResponseEntity<RoleResponse[]> getRoleResponseEntity() {
        return ResponseEntity.ok(List.of(getRoleResponse()).toArray(RoleResponse[]::new));
    }

    private static RoleResponse getRoleResponse() {
        return RoleResponse.builder()
                .roleId(1L)
                .name("ROLE_USER")
                .build();
    }

}