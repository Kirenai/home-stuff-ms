package me.kirenai.re.auth.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kirenai.re.auth.dto.LoginRequest;
import me.kirenai.re.auth.dto.RegisterRequest;
import me.kirenai.re.auth.dto.UserResponse;

import java.io.IOException;

public class AuthMocks {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private AuthMocks() {
    }

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

    public static UserResponse getUserResponse() throws IOException {
        return objectMapper.readValue(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("mock/user/UserResponse.json"), UserResponse.class);
    }
}
