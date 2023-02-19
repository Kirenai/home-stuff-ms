package me.kirenai.re.auth.util;

import me.kirenai.re.auth.dto.ApiResponse;
import me.kirenai.re.auth.dto.LoginRequest;
import me.kirenai.re.auth.dto.RegisterRequest;
import me.kirenai.re.auth.dto.UserResponse;

public class AuthMocks {

    public static LoginRequest getLoginRequest() {
        return LoginRequest.builder()
                .username("username")
                .password("password")
                .build();
    }

    public static RegisterRequest getRegisterRequest() {
        return RegisterRequest.builder()
                .username("username")
                .password("password")
                .firstName("firstname")
                .lastName("lastname")
                .age(18)
                .build();
    }

    public static ApiResponse<UserResponse> getApiResponse() {
        return ApiResponse.<UserResponse>builder()
                .response(UserResponse.builder().userId(1L).username("username").firstName("firstname").lastName("lastname").age(18).build())
                .build();
    }
}
