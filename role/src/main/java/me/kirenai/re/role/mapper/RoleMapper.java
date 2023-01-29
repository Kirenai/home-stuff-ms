package me.kirenai.re.role.mapper;

import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.role.dto.RoleRequest;
import me.kirenai.re.role.dto.RoleResponse;
import me.kirenai.re.role.entity.Role;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;

@Slf4j
@Mapper(componentModel = "spring")
public abstract class RoleMapper {

    public abstract Role mapInRoleRequestToRole(RoleRequest roleRequest);

    public abstract RoleResponse mapOutRoleToRoleResponse(Role role);

    @BeforeMapping
    public void mapInLog(RoleRequest roleRequest) {
        log.info("Invoking RoleMapper.mapInRoleRequestToRole method");
    }

    @BeforeMapping
    public void mapInLog(Role role) {
        log.info("Invoking RoleMapper.mapOutRoleToRoleResponse method");
    }

}