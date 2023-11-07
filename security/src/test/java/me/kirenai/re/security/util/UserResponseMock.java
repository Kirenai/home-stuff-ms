package me.kirenai.re.security.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kirenai.re.security.dto.UserResponse;

import java.io.IOException;

public class UserResponseMock {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final UserResponseMock INSTANCE = new UserResponseMock();

    public static UserResponseMock getInstance() {
        return INSTANCE;
    }

    public UserResponse getUserResponse() throws IOException {
        return this.objectMapper.readValue(
                Thread.currentThread().getContextClassLoader().getResourceAsStream("mock/user/UserResponse.json"),
                UserResponse.class
        );
    }

}
