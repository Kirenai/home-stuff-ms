package me.kirenai.re.auth.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.auth.dto.LoginRequest;
import me.kirenai.re.auth.dto.RegisterRequest;
import me.kirenai.re.auth.service.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthHandler {

    private final AuthService authService;

    public Mono<ServerResponse> login(ServerRequest request) {
        log.info("Invoking AuthHandler.login method");
        return request.bodyToMono(LoginRequest.class)
                .flatMap(this.authService::login)
                .flatMap(token -> ServerResponse.status(HttpStatus.OK)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .bodyValue("Token in header"));
    }

    public Mono<ServerResponse> register(ServerRequest request) {
        log.info("Invoking AuthHandler.register method");
        return request.bodyToMono(RegisterRequest.class)
                .flatMap(this.authService::register)
                .flatMap(userResponse -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(userResponse));
    }

}