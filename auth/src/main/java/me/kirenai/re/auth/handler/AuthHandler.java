package me.kirenai.re.auth.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.auth.dto.LoginRequest;
import me.kirenai.re.auth.service.AuthService;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import me.kirenai.re.security.service.AuthUserDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthHandler {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    public Mono<ServerResponse> login(ServerRequest request) {
        log.info("Invoking AuthHandler.login method");
        return request.bodyToMono(LoginRequest.class)
                .flatMap(loginRequest -> this.authService.login(loginRequest)
                        .flatMap(authentication -> {
                            AuthUserDetails principal = (AuthUserDetails) authentication.getPrincipal();
                            String token = this.jwtTokenProvider.generateJwtToken(authentication);
                            return ServerResponse.status(HttpStatus.CREATED)
                                    .header(HttpHeaders.AUTHORIZATION, this.jwtTokenProvider.getTokenPrefix() + token)
                                    .bodyValue(principal);
                        })
                );
    }

}