package me.kirenai.re.auth.service;

import me.kirenai.re.auth.api.UserManager;
import me.kirenai.re.auth.dto.LoginRequest;
import me.kirenai.re.auth.dto.RegisterRequest;
import me.kirenai.re.auth.dto.UserResponse;
import me.kirenai.re.auth.util.AuthMocks;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import me.kirenai.re.security.service.AuthUserDetails;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;
    @Mock
    private ReactiveAuthenticationManager authenticationManager;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private UserManager userManager;

    @Test
    @DisplayName("Should login")
    public void loginTest() {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                new AuthUserDetails(1L, "username", "password", List.of(new SimpleGrantedAuthority("ROLE_USER"))),
                "password"
        );
        when(this.authenticationManager.authenticate(any())).thenReturn(Mono.just(authentication));
        Mono<Authentication> authenticationMono = this.authService.login(new LoginRequest());

        StepVerifier.create(authenticationMono.log())
                .expectNext(authentication)
                .verifyComplete();

        verify(this.authenticationManager, timeout(1)).authenticate(any());
    }

    @Test
    @DisplayName("Should register")
    public void registerTest() throws IOException {
        UserResponse userResponse = AuthMocks.getUserResponse();
        when(this.userManager.createUser(any())).thenReturn(Mono.just(userResponse));

        Mono<UserResponse> response = this.authService.register(new RegisterRequest());

        StepVerifier.create(response)
                .expectNext(userResponse)
                .verifyComplete();

        verify(this.userManager, timeout(1)).createUser(any());
    }

}