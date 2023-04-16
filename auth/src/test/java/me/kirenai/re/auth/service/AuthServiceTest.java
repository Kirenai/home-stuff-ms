package me.kirenai.re.auth.service;

import me.kirenai.re.auth.dto.ApiResponse;
import me.kirenai.re.auth.dto.LoginRequest;
import me.kirenai.re.auth.dto.RegisterRequest;
import me.kirenai.re.auth.util.AuthMocks;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import me.kirenai.re.security.service.AuthUserDetails;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;
    @Mock
    private ReactiveAuthenticationManager authenticationManager;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

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

//    @Test
//    @DisplayName("Should register")
//    public void registerTest() {
//        when(this.restTemplate.exchange(anyString(), any(), any(), eq(ApiResponse.class)))
//                .thenReturn(ResponseEntity.ok(AuthMocks.getApiResponse()));
//        this.authService.register(new RegisterRequest());
//        verify(this.jwtTokenProvider, timeout(1)).generateInternalJwtToken();
//        verify(this.jwtTokenProvider, timeout(1)).getTokenPrefix();
//        verify(this.restTemplate, timeout(1)).exchange(anyString(), any(), any(), eq(ApiResponse.class));
//    }

}