package me.kirenai.re.role.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.exception.role.RoleNotFoundExceptionFactory;
import me.kirenai.re.role.dto.RoleRequest;
import me.kirenai.re.role.dto.RoleResponse;
import me.kirenai.re.role.entity.Role;
import me.kirenai.re.role.entity.RoleUser;
import me.kirenai.re.role.mapper.RoleMapper;
import me.kirenai.re.role.repository.RoleRepository;
import me.kirenai.re.role.repository.RoleUserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {

    public static final String DEFAULT_ROLE = "ROLE_USER";

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final RoleUserRepository roleUserRepository;

//    public List<RoleResponse> findAll(Pageable pageable) {
//        log.info("Invoking RoleService.findAll method");
//        return this.roleRepository.findAll(pageable)
//                .getContent()
//                .stream()
//                .map(this.roleMapper::mapOutRoleToRoleResponse)
//                .toList();
//    }

    public Mono<RoleResponse> findOne(Long roleId) {
        log.info("Invoking RoleService.findOne method");
        return this.roleRepository.findById(roleId)
                .switchIfEmpty(Mono.error(new RoleNotFoundExceptionFactory(String.format("Role not found with id: %s", roleId))))
                .map(this.roleMapper::mapOutRoleToRoleResponse);
    }

    public Flux<RoleResponse> findAllByUserId(Long userId) {
        log.info("Invoking RoleService.findAllByUserId method");
        return this.roleUserRepository.findByUserId(userId)
                .flatMap(roleUser -> Flux.fromIterable(roleUser.getRoles()))
                .map(this.roleMapper::mapOutRoleToRoleResponse);
    }

    public Mono<RoleResponse> create(RoleRequest roleRequest) {
        log.info("Invoking RoleService.create method");
        Role role = this.roleMapper.mapInRoleRequestToRole(roleRequest);
        return this.roleRepository.save(role)
                .map(this.roleMapper::mapOutRoleToRoleResponse);
    }

    public Mono<Void> createRoleUser(Long userId) {
        log.info("Invoking RoleService.createRoleUser method");
        return this.roleRepository.findByName(DEFAULT_ROLE)
                .switchIfEmpty(Mono.error(new RoleNotFoundExceptionFactory(String.format("Role not found by name: %s", DEFAULT_ROLE))))
                .flatMap(role -> {
                    RoleUser roleUser = RoleUser.builder()
                            .roleId(role.getRoleId())
                            .userId(userId)
                            .build();
                    return this.roleUserRepository.saveRoleUser(roleUser);
                }).flatMap(roleUser -> Mono.empty());
    }

    public Mono<RoleResponse> update(Long roleId, RoleRequest roleRequest) {
        log.info("Invoking RoleService.update method");
        return this.roleRepository.findById(roleId)
                .switchIfEmpty(Mono.error(new RoleNotFoundExceptionFactory(String.format("Role not found by role id: %s", roleId))))
                .flatMap(role -> {
                    role.setName(roleRequest.getName());
                    return this.roleRepository.save(role);
                })
                .map(this.roleMapper::mapOutRoleToRoleResponse);
    }

}