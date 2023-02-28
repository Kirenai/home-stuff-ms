package me.kirenai.re.role.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.role.dto.RoleRequest;
import me.kirenai.re.role.dto.RoleResponse;
import me.kirenai.re.role.entity.Role;
import me.kirenai.re.role.entity.RoleUser;
import me.kirenai.re.role.entity.RoleUserId;
import me.kirenai.re.role.mapper.RoleMapper;
import me.kirenai.re.role.repository.RoleRepository;
import me.kirenai.re.role.repository.RoleUserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {

    public static final String DEFAULT_ROLE = "ROLE_USER";

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final RoleUserRepository roleUserRepository;

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

    public List<RoleResponse> findAllByUserId(Long userId) {
        log.info("Invoking RoleService.findAllByUserId method");
        return this.roleUserRepository.findByIdUserId(userId)
                .stream()
                .map(RoleUser::getRole)
                .map(this.roleMapper::mapOutRoleToRoleResponse)
                .toList();
    }

    public RoleResponse create(RoleRequest roleRequest) {
        log.info("Invoking RoleService.create method");
        Role role = this.roleMapper.mapInRoleRequestToRole(roleRequest);
        this.roleRepository.save(role);
        return this.roleMapper.mapOutRoleToRoleResponse(role);
    }

    public void createRoleUser(Long userId) {
        log.info("Invoking RoleService.createRoleUser method");
        Role role = this.roleRepository.findByName(DEFAULT_ROLE).orElseThrow();
        RoleUserId id = RoleUserId.builder()
                .roleId(role.getRoleId())
                .userId(userId)
                .build();
        RoleUser roleUser = RoleUser.builder()
                .id(id)
                .build();
        this.roleUserRepository.save(roleUser);
    }

    public RoleResponse update(Long roleId, RoleRequest roleRequest) {
        log.info("Invoking RoleService.update method");
        Role role = this.roleRepository.findById(roleId).orElseThrow();
        role.setName(roleRequest.getName());
        this.roleRepository.save(role);
        return this.roleMapper.mapOutRoleToRoleResponse(role);
    }

}