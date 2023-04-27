package me.kirenai.re.role.util;

import me.kirenai.re.role.dto.RoleRequest;
import me.kirenai.re.role.dto.RoleResponse;
import me.kirenai.re.role.entity.Role;
import me.kirenai.re.role.entity.RoleUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RoleMocks {

    public static RoleResponse getRoleResponse() {
        return getRoleResponseList().get(0);
    }

    public static List<RoleResponse> getRoleResponseList() {
        return List.of(
                getRoleResponseMock(1L, "ROLE_USER"),
                getRoleResponseMock(2L, "ROLE_MODERATOR"),
                getRoleResponseMock(3L, "ROLE_ADMIN")
        );
    }

    public static RoleResponse getRoleResponseMock(Long roleId, String name) {
        return RoleResponse.builder()
                .roleId(roleId)
                .name(name)
                .build();
    }

    public static RoleRequest getRoleRequest() {
        return RoleRequest.builder()
                .name("name1")
                .build();
    }

    public static Role getRole() {
        return getRolePage().getContent().get(0);
    }

    public static Page<Role> getRolePage() {
        return new PageImpl<>(Arrays.asList(
                getRoleMock(1L, "ROLE_USER"),
                getRoleMock(2L, "ROLE_MODERATOR"),
                getRoleMock(3L, "ROLE_ADMIN")
        ));
    }

    private static Role getRoleMock(Long roleId, String name) {
        return Role.builder()
                .roleId(roleId)
                .name(name)
                .build();
    }

    public static RoleUser getRoleUserMock() {
        return RoleUser.builder()
                .userId(1L)
                .roleId(getRole().getRoleId())
                .build();
    }

    public static RoleUser getRoleUserMockList() {
        return RoleUser.builder()
                .roles(Collections.singletonList(getRolePage().getContent().get(0)))
                .build();
    }

}