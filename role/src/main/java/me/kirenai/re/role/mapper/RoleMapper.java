package me.kirenai.re.role.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.role.dto.RoleRequest;
import me.kirenai.re.role.dto.RoleResponse;
import me.kirenai.re.role.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoleMapper {

    private final ModelMapper modelMapper;

    public Role mapInRoleRequestToRole(RoleRequest roleRequest) {
        log.info("Invoking RoleMapper.mapInRoleRequestToRole method");
        return this.modelMapper.map(roleRequest, Role.class);
    }

    public RoleResponse mapOutRoleToRoleResponse(Role role) {
        log.info("Invoking RoleMapper.mapOutRoleToRoleResponse method");
        return this.modelMapper.map(role, RoleResponse.class);
    }

}