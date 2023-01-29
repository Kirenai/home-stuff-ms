package me.kirenai.re.role.mapper;

import me.kirenai.re.role.dto.RoleRequest;
import me.kirenai.re.role.dto.RoleResponse;
import me.kirenai.re.role.entity.Role;
import me.kirenai.re.role.util.RoleMocks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RoleMapperTest {

    private final RoleMapper roleMapper = Mappers.getMapper(RoleMapper.class);

    @Test
    @DisplayName("mapInTest")
    void mapInRoleRequestToRoleTest() {
        RoleRequest input = RoleMocks.getRoleRequest();
        Role request = this.roleMapper.mapInRoleRequestToRole(input);

        assertNotNull(request);
        assertNotNull(request.getName());

        assertEquals(input.getName(), request.getName());
    }

    @Test
    @DisplayName("mapInEmptyTest")
    void mapInRoleRequestToRoleEmptyTest() {
        Role request = this.roleMapper.mapInRoleRequestToRole(new RoleRequest());

        assertNotNull(request);
    }

    @Test
    @DisplayName("mapOutTest")
    void mapOutRoleToRoleResponseTest() {
        Role input = RoleMocks.getRole();
        RoleResponse response = this.roleMapper.mapOutRoleToRoleResponse(input);

        assertNotNull(response);
        assertNotNull(response.getRoleId());
        assertNotNull(response.getName());

        assertEquals(input.getRoleId(), response.getRoleId());
        assertEquals(input.getName(), response.getName());
    }

    @Test
    @DisplayName("mapOutEmptyTest")
    void mapOutRoleToRoleResponseEmptyTest() {
        RoleResponse response = this.roleMapper.mapOutRoleToRoleResponse(new Role());

        assertNotNull(response);
    }

}