package me.kirenai.re.role.service;

import me.kirenai.re.role.dto.RoleResponse;
import me.kirenai.re.role.mapper.RoleMapper;
import me.kirenai.re.role.repository.RoleRepository;
import me.kirenai.re.role.util.RoleMocks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @InjectMocks
    private RoleService roleService;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private RoleMapper roleMapper;

    @Test
    @DisplayName("Should find a list of roles")
    void shouldFindAllRolesTest() {
        List<RoleResponse> roleResponseList = RoleMocks.getRoleResponseList();
        Pageable pageableMock = mock(Pageable.class);

        when(this.roleRepository.findAll(any(Pageable.class)))
                .thenReturn(RoleMocks.getRolePage());
        when(this.roleMapper.mapOutRoleToRoleResponse(any()))
                .thenReturn(roleResponseList.get(0), roleResponseList.get(1), roleResponseList.get(2));

        List<RoleResponse> response = this.roleService.findAll(pageableMock);

        assertNotNull(response);
        assertEquals(roleResponseList.size(), response.size());
        assertEquals(roleResponseList, response);

        verify(this.roleRepository, times(1)).findAll(any(Pageable.class));
        verify(this.roleMapper, times(3)).mapOutRoleToRoleResponse(any());
    }

    @Test
    @DisplayName("Should find a role when finding one")
    void shouldFindRoleWhenFindingOne() {
        RoleResponse roleResponse = RoleMocks.getRoleResponse();

        when(this.roleRepository.findById(anyLong()))
                .thenReturn(Optional.of(RoleMocks.getRole()));
        when(this.roleMapper.mapOutRoleToRoleResponse(any()))
                .thenReturn(roleResponse);

        RoleResponse response = this.roleService.findOne(1L);

        assertEquals(roleResponse, response);

        verify(this.roleRepository).findById(anyLong());
        verify(this.roleMapper).mapOutRoleToRoleResponse(any());
    }

    @Test
    @DisplayName("Should create a role")
    void shouldCreateRoleTest() {
        RoleResponse roleResponse = RoleMocks.getRoleResponse();

        when(this.roleMapper.mapInRoleRequestToRole(any())).thenReturn(RoleMocks.getRole());
        when(this.roleMapper.mapOutRoleToRoleResponse(any())).thenReturn(roleResponse);

        RoleResponse response = this.roleService.create(RoleMocks.getRoleRequest());

        assertEquals(roleResponse, response);

        verify(this.roleMapper).mapInRoleRequestToRole(any());
        verify(this.roleMapper).mapOutRoleToRoleResponse(any());
    }

    @Test
    @DisplayName("Should update a role")
    void shouldUpdateRoleTest() {
        RoleResponse roleResponse = RoleMocks.getRoleResponse();

        when(this.roleRepository.findById(anyLong())).thenReturn(Optional.of(RoleMocks.getRole()));
        when(this.roleMapper.mapOutRoleToRoleResponse(any())).thenReturn(roleResponse);

        RoleResponse response = this.roleService.update(1L, RoleMocks.getRoleRequest());

        assertEquals(roleResponse, response);

        verify(this.roleRepository, times(1)).findById(anyLong());
        verify(this.roleMapper, times(1)).mapOutRoleToRoleResponse(any());
    }

}