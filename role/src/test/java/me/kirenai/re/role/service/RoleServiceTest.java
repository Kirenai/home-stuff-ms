package me.kirenai.re.role.service;

import me.kirenai.re.role.dto.RoleResponse;
import me.kirenai.re.role.mapper.RoleMapper;
import me.kirenai.re.role.repository.RoleRepository;
import me.kirenai.re.role.repository.RoleUserRepository;
import me.kirenai.re.role.util.RoleMocks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

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
    private RoleUserRepository roleUserRepository;
    @Mock
    private RoleMapper roleMapper;

//    @Test
//    @DisplayName("Should find a list of roles")
//    void shouldFindAllRolesTest() {
//        List<RoleResponse> roleResponseList = RoleMocks.getRoleResponseList();
//        Pageable pageableMock = mock(Pageable.class);
//
//        when(this.roleRepository.findAll(any(Pageable.class)))
//                .thenReturn(RoleMocks.getRolePage());
//        when(this.roleMapper.mapOutRoleToRoleResponse(any()))
//                .thenReturn(roleResponseList.get(0), roleResponseList.get(1), roleResponseList.get(2));
//
//        List<RoleResponse> response = this.roleService.findAll(pageableMock);
//
//        assertNotNull(response);
//        assertEquals(roleResponseList.size(), response.size());
//        assertEquals(roleResponseList, response);
//
//        verify(this.roleRepository, times(1)).findAll(any(Pageable.class));
//        verify(this.roleMapper, times(3)).mapOutRoleToRoleResponse(any());
//    }

    @Test
    @DisplayName("Should find a role when finding one")
    void shouldFindRoleWhenFindingOne() {
        RoleResponse roleResponse = RoleMocks.getRoleResponse();

        when(this.roleRepository.findById(anyLong()))
                .thenReturn(Mono.just(RoleMocks.getRole()));
        when(this.roleMapper.mapOutRoleToRoleResponse(any()))
                .thenReturn(roleResponse);

        Mono<RoleResponse> response = this.roleService.findOne(1L);

        StepVerifier
                .create(response)
                .expectNext(roleResponse)
                .verifyComplete();

        verify(this.roleRepository, times(1)).findById(anyLong());
        verify(this.roleMapper, times(1)).mapOutRoleToRoleResponse(any());
    }

    @Test
    @DisplayName("Should find a list of roles by userId")
    void shouldFindRolesByUserIdTest() {
        RoleResponse roleResponse = RoleMocks.getRoleResponse();

        when(this.roleUserRepository.findByUserId(anyLong()))
                .thenReturn(Flux.just(RoleMocks.getRoleUserMockList()));
        when(this.roleMapper.mapOutRoleToRoleResponse(any()))
                .thenReturn(roleResponse);

        Flux<RoleResponse> response = this.roleService.findAllByUserId(1L);

        StepVerifier
                .create(response)
                .expectNext(roleResponse)
                .verifyComplete();

        verify(this.roleUserRepository, times(1)).findByUserId(anyLong());
        verify(this.roleMapper, times(1)).mapOutRoleToRoleResponse(any());
    }

    @Test
    @DisplayName("Should create a role")
    void shouldCreateRoleTest() {
        RoleResponse roleResponse = RoleMocks.getRoleResponse();

        when(this.roleMapper.mapInRoleRequestToRole(any())).thenReturn(RoleMocks.getRole());
        when(this.roleRepository.save(any())).thenReturn(Mono.just(RoleMocks.getRole()));
        when(this.roleMapper.mapOutRoleToRoleResponse(any())).thenReturn(roleResponse);

        Mono<RoleResponse> response = this.roleService.create(RoleMocks.getRoleRequest());

        StepVerifier
                .create(response)
                .expectNext(roleResponse)
                .verifyComplete();

        verify(this.roleMapper, times(1)).mapInRoleRequestToRole(any());
        verify(this.roleRepository, times(1)).save(any());
        verify(this.roleMapper, times(1)).mapOutRoleToRoleResponse(any());
    }

    @Test
    @DisplayName("Should create role user")
    void shouldCreateRoleUserTest() {
        when(this.roleRepository.findByName(anyString()))
                .thenReturn(Mono.just(RoleMocks.getRole()));
        when(this.roleUserRepository.saveRoleUser(any())).thenReturn(Mono.just(RoleMocks.getRoleUserMock()));

        Mono<Void> response = this.roleService.createRoleUser(1L);

        StepVerifier
                .create(response)
                .verifyComplete();

        verify(this.roleRepository, times(1)).findByName(anyString());
        verify(this.roleUserRepository, times(1)).saveRoleUser(any());
    }

    @Test
    @DisplayName("Should update a role")
    void shouldUpdateRoleTest() {
        RoleResponse roleResponse = RoleMocks.getRoleResponse();

        when(this.roleRepository.findById(anyLong())).thenReturn(Mono.just(RoleMocks.getRole()));
        when(this.roleRepository.save(any())).thenReturn(Mono.just(RoleMocks.getRole()));
        when(this.roleMapper.mapOutRoleToRoleResponse(any())).thenReturn(roleResponse);

        Mono<RoleResponse> response = this.roleService.update(1L, RoleMocks.getRoleRequest());

        StepVerifier
                .create(response)
                .expectNext(roleResponse)
                .verifyComplete();

        verify(this.roleRepository, times(1)).findById(anyLong());
        verify(this.roleRepository, times(1)).save(any());
        verify(this.roleMapper, times(1)).mapOutRoleToRoleResponse(any());
    }

}