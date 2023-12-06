package me.kirenai.re.auth.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.auth.dto.LoginRequest;
import me.kirenai.re.auth.dto.RegisterRequest;
import me.kirenai.re.auth.dto.UserResponse;
import me.kirenai.re.auth.service.AuthService;
import me.kirenai.re.security.dto.ErrorResponse;
import me.kirenai.re.security.validator.Validator;
import org.springframework.http.HttpHeaders;
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
public class AuthHandler {

    private final AuthService authService;
    private final Validator validator;

    public Mono<ServerResponse> login(ServerRequest request) {
        log.info("Invoking AuthHandler.login method");
        return request.bodyToMono(LoginRequest.class)
                .flatMap(loginRequest -> {
                    List<ErrorResponse> errors = this.validator.validate(loginRequest);
                    if (!errors.isEmpty()) {
                        return ServerResponse.badRequest().bodyValue(errors);
                    }
                    return this.authService.login(loginRequest)
                            .flatMap(token ->
                                    ServerResponse.status(HttpStatus.OK)
                                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                                            .build()
                            );
                });
    }

    public Mono<ServerResponse> register(ServerRequest request) {
        log.info("Invoking AuthHandler.register method");
        return request.bodyToMono(RegisterRequest.class)
                .flatMap(registerRequest -> {
                    List<ErrorResponse> errors = this.validator.validate(registerRequest);
                    if (!errors.isEmpty()) {
                        return ServerResponse.badRequest().bodyValue(errors);
                    }
                    Mono<UserResponse> response = this.authService.register(registerRequest);
                    return ServerResponse.status(HttpStatus.CREATED)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(response, UserResponse.class);
                });
    }

}