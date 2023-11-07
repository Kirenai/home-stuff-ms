package me.kirenai.re.security.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kirenai.re.security.dto.RoleResponse;

import java.io.IOException;

public class RoleMocks {

    private static final RoleMocks INSTANCE = new RoleMocks();

    private final ObjectMapper objectMapper = new ObjectMapper();

    public static RoleMocks getInstance() {
        return INSTANCE;
    }

    public RoleResponse getRoleResponse() throws IOException {
        return this.objectMapper.readValue(
                Thread.currentThread().getContextClassLoader().getResourceAsStream("mock/role/RoleResponse.json"),
                RoleResponse.class
        );
    }

}
