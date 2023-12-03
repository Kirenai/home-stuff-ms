package me.kirenai.re.auth.service;

import me.kirenai.re.auth.api.RoleUserManager;
import me.kirenai.re.auth.api.UserManager;
import me.kirenai.re.auth.dto.LoginRequest;
import me.kirenai.re.auth.dto.RegisterRequest;
import me.kirenai.re.auth.dto.UserResponse;
import me.kirenai.re.auth.util.AuthMocks;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;
    @Mock
    private UserManager userManager;
    @Mock
    private RoleUserManager roleUserManager;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("Should login")
    public void loginTest() throws IOException {
        String token = "token";
        when(this.userManager.getUserByUsername(anyString())).thenReturn(Mono.just(AuthMocks.getUserResponse()));
        when(this.roleUserManager.getRolesByUserId(anyLong())).thenReturn(Mono.just(Arrays.asList("ROLE_USER", "ROLE_ANY")));
        when(this.jwtTokenProvider.generateJwtToken(anyString(), anyString(), anyList())).thenReturn(token);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        Mono<String> tokenMono = this.authService.login(loginRequest);

        StepVerifier.create(tokenMono)
                .expectNext(token)
                .verifyComplete();

        verify(this.userManager, times(1)).getUserByUsername(anyString());
        verify(this.roleUserManager, times(1)).getRolesByUserId(anyLong());
        verify(this.jwtTokenProvider, times(1)).generateJwtToken(anyString(), anyString(), anyList());
    }

    @Test
    @DisplayName("Should register")
    public void registerTest() throws IOException {
        UserResponse userResponse = AuthMocks.getUserResponse();
        when(this.userManager.postCreateUser(any())).thenReturn(Mono.just(userResponse));

        Mono<UserResponse> response = this.authService.register(new RegisterRequest());

        StepVerifier.create(response)
                .expectNext(userResponse)
                .verifyComplete();

        verify(this.userManager, timeout(1)).postCreateUser(any());
    }

}