package me.kirenai.re.role.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.role.dto.RoleRequest;
import me.kirenai.re.role.dto.RoleResponse;
import me.kirenai.re.role.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoleHandler {

    private final RoleService roleService;

    public Mono<ServerResponse> getRole(ServerRequest request) {
        log.info("Invoking RoleHandler.getRole method");
        String roleId = request.pathVariable("roleId");
        return this.roleService.findOne(Long.parseLong(roleId))
                .flatMap(role -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(role));
    }

    public Mono<ServerResponse> getRolesByUserId(ServerRequest request) {
        log.info("Invoking RoleHandler.getRolesByUserId method");
        String userId = request.pathVariable("userId");
        Mono<List<RoleResponse>> roleResponse = this.roleService.findAllByUserId(Long.parseLong(userId)).collectList();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(roleResponse, RoleResponse.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        log.info("Invoking RoleHandler.save method");
        return request.bodyToMono(RoleRequest.class)
                .flatMap(roleRequest -> this.roleService.create(roleRequest)
                        .flatMap(roleResponse -> ServerResponse.status(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(roleResponse)));
    }

    public Mono<ServerResponse> saveRoleUser(ServerRequest request) {
        log.info("Invoking RoleHandler.saveRoleUser method");
        String userId = request.pathVariable("userId");
        return this.roleService.createRoleUser(Long.parseLong(userId))
                .flatMap(roleResponse -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(Mono.empty()));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        log.info("Invoking RoleHandler.update method");
        String roleId = request.pathVariable("roleId");
        return request.bodyToMono(RoleRequest.class)
                .flatMap(roleRequest -> this.roleService.update(Long.parseLong(roleId), roleRequest)
                        .flatMap(roleResponse -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(roleResponse)));
    }

}