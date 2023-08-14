package me.kirenai.re.user.handler;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.user.dto.UserRequest;
import me.kirenai.re.user.dto.UserResponse;
import me.kirenai.re.user.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserHandler {

    private final UserService userService;
    private final Validator validator;

    public Mono<ServerResponse> getAll(ServerRequest request) {
        log.info("Invoking UserHandler.getAll method");
        int page = Integer.parseInt(request.queryParam("page").orElse("0"));
        int size = Integer.parseInt(request.queryParam("size").orElse("10"));
        String[] sort = request.queryParam("sort").orElse("userId,ASC").split(",");
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.valueOf(sort[1]), sort[0]));
        Flux<UserResponse> response = this.userService.findAll(pageable);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(response, UserResponse.class);
    }

    public Mono<ServerResponse> getOne(ServerRequest request) {
        log.info("Invoking UserHandler.getOne method");
        String userId = request.pathVariable("userId");
        Mono<UserResponse> response = this.userService.findOne(Long.parseLong(userId));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(response, UserResponse.class);
    }

    public Mono<ServerResponse> getOneByUsername(ServerRequest request) {
        log.info("Invoking UserHandler.getOneByUsername method");
        String username = request.pathVariable("username");
        Mono<me.kirenai.re.security.dto.UserResponse> response = this.userService.findByUsername(username);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(response, me.kirenai.re.security.dto.UserResponse.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        log.info("Invoking UserHandler.save method");
        Mono<UserRequest> userRequestMono = request.bodyToMono(UserRequest.class);
        String token = request.headers().firstHeader(HttpHeaders.AUTHORIZATION);
        return userRequestMono.flatMap(userRequest -> {
            BindingResult bindingResult = new BeanPropertyBindingResult(userRequest, UserRequest.class.getName());
            validator.validate(bindingResult);
            if (bindingResult.hasErrors()) {
                return ServerResponse.badRequest().bodyValue(bindingResult.getAllErrors());
            }
            return this.userService.create(userRequest, token)
                    .flatMap(userResponse -> ServerResponse
                            .status(HttpStatus.CREATED)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(userResponse)
                    );
        });
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        log.info("Invoking UserHandler.update method");
        String userId = request.pathVariable("userId");
        Mono<UserRequest> userRequest = request.bodyToMono(UserRequest.class);
        return userRequest.flatMap(userR -> {
            BindingResult bindingResult = new BeanPropertyBindingResult(userR, UserRequest.class.getName());
            validator.validate(bindingResult);
            if (bindingResult.hasErrors()) {
                return ServerResponse.badRequest().bodyValue(bindingResult.getAllErrors());
            }
            Mono<UserResponse> response = this.userService.update(Long.parseLong(userId), userR);
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(response);
        });
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        log.info("Invoking UserHandler.delete method");
        String userId = request.pathVariable("userId");
        Mono<Void> response = this.userService.delete(Long.parseLong(userId));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(response, UserResponse.class);
    }

}