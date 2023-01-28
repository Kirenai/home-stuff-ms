package me.kirenai.re.role.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.role.dto.RoleRequest;
import me.kirenai.re.role.dto.RoleResponse;
import me.kirenai.re.role.entity.Role;
import me.kirenai.re.role.mapper.RoleMapper;
import me.kirenai.re.role.repository.RoleRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public List<RoleResponse> findAll(Pageable pageable) {
        log.info("Invoking RoleService.findAll method");
        return this.roleRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(this.roleMapper::mapOutRoleToRoleResponse)
                .toList();
    }

    public RoleResponse findOne(Long roleId) {
        log.info("Invoking RoleService.findOne method");
        Role role = this.roleRepository.findById(roleId).orElseThrow();
        return this.roleMapper.mapOutRoleToRoleResponse(role);
    }

    public RoleResponse create(RoleRequest roleRequest) {
        Role role = this.roleMapper.mapInRoleRequestToRole(roleRequest);
        this.roleRepository.save(role);
        return this.roleMapper.mapOutRoleToRoleResponse(role);
    }


    public RoleResponse update(Long roleId, RoleRequest roleRequest) {
        Role role = this.roleRepository.findById(roleId).orElseThrow();
        role.setName(roleRequest.getName());
        this.roleRepository.save(role);
        return this.roleMapper.mapOutRoleToRoleResponse(role);
    }

}