package me.kirenai.re.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.auth.api.RoleUserManager;
import me.kirenai.re.auth.api.UserManager;
import me.kirenai.re.auth.dto.LoginRequest;
import me.kirenai.re.auth.dto.RegisterRequest;
import me.kirenai.re.auth.dto.UserResponse;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserManager userManager;
    private final RoleUserManager roleUserManager;
    private final JwtTokenProvider jwtTokenProvider;

    public Mono<String> login(LoginRequest loginRequest) {
        log.info("Invoking AuthService.login method");
        Mono<UserResponse> userResponseMono = this.userManager.getUserByUsername(loginRequest.getUsername());
        return userResponseMono
                .flatMap(userResponse -> this.roleUserManager.getRolesByUserId(userResponse.getUserId())
                        .map(roles ->
                                this.jwtTokenProvider.generateJwtToken(
                                        String.valueOf(userResponse.getUserId()),
                                        userResponse.getUsername(),
                                        roles
                                )
                        )
                );
    }

    public Mono<UserResponse> register(RegisterRequest request) {
        log.info("Invoking AuthService.register method");
        return this.userManager.postCreateUser(request);
    }

}