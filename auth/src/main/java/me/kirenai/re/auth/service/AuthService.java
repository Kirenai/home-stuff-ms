package me.kirenai.re.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.auth.api.UserManager;
import me.kirenai.re.auth.dto.LoginRequest;
import me.kirenai.re.auth.dto.RegisterRequest;
import me.kirenai.re.auth.dto.UserResponse;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final ReactiveAuthenticationManager authenticationManager;
    private final UserManager userManager;

    public Mono<Authentication> login(LoginRequest loginRequest) {
        log.info("Invoking AuthService.login method");
        return this.authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(),
                                loginRequest.getPassword()
                        ))
                .map(authentication -> {
                    ReactiveSecurityContextHolder.withAuthentication(authentication);
                    return authentication;
                });
    }

    public Mono<UserResponse> register(RegisterRequest request) {
        log.info("Invoking AuthService.register method");
        return this.userManager.createUser(request);
    }

}