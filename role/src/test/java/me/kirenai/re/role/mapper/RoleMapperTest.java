package me.kirenai.re.role.mapper;

import me.kirenai.re.role.dto.RoleRequest;
import me.kirenai.re.role.dto.RoleResponse;
import me.kirenai.re.role.entity.Role;
import me.kirenai.re.role.util.RoleMocks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RoleMapperTest {

    @InjectMocks
    private RoleMapper roleMapper;
    @Spy
    private ModelMapper modelMapper;

    @Test
    @DisplayName("mapInTest")
    void mapInRoleRequestToRoleTest() {
        RoleRequest input = RoleMocks.getRoleRequest();
        Role request = this.roleMapper.mapInRoleRequestToRole(input);

        assertNotNull(request);
        assertNotNull(request.getName());

        assertEquals(input.getName(), request.getName());

        verify(this.modelMapper, timeout(1)).map(any(), any());
    }

    @Test
    @DisplayName("mapInEmptyTest")
    void mapInRoleRequestToRoleEmptyTest() {
        Role request = this.roleMapper.mapInRoleRequestToRole(new RoleRequest());

        assertNotNull(request);

        verify(this.modelMapper, timeout(1)).map(any(), any());
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

        verify(this.modelMapper, timeout(1)).map(any(), any());
    }

    @Test
    @DisplayName("mapOutEmptyTest")
    void mapOutRoleToRoleResponseEmptyTest() {
        RoleResponse response = this.roleMapper.mapOutRoleToRoleResponse(new Role());

        assertNotNull(response);

        verify(this.modelMapper, timeout(1)).map(any(), any());
    }

}