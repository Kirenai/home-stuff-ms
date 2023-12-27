package me.kirenai.re.nourishment.util;

import me.kirenai.re.nourishment.dto.UserResponse;
import me.kirenai.re.nourishment.util.helper.ObjectMapperHelper;

import java.io.IOException;

public class UserMocks {

    private static final UserMocks INSTANCE = new UserMocks();

    private final ObjectMapperHelper mapper = new ObjectMapperHelper();

    public static UserMocks getInstance() {
        return INSTANCE;
    }

    public UserResponse getUserResponse() throws IOException {
        return this.mapper.readValue(
                Thread.currentThread().getContextClassLoader().getResourceAsStream("mock/user/UserResponse.json"),
                UserResponse.class
        );
    }

}