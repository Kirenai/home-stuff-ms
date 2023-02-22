package me.kirenai.re.auth.service;

import me.kirenai.re.auth.dto.LoginRequest;
import me.kirenai.re.auth.dto.RegisterRequest;
import me.kirenai.re.auth.util.AuthMocks;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("Should login")
    public void loginTest() {
        this.authService.login(new LoginRequest());
        verify(this.authenticationManager, timeout(1)).authenticate(any());
    }

    @Test
    @DisplayName("Should register")
    public void registerTest() {
        when(this.restTemplate.postForEntity(anyString(), any(), any(), anyMap()))
                .thenReturn(ResponseEntity.ok(AuthMocks.getApiResponse()));
        this.authService.register(new RegisterRequest());
        verify(this.jwtTokenProvider, timeout(1)).generateInternalJwtToken();
        verify(this.jwtTokenProvider, timeout(1)).getTokenPrefix();
        verify(this.restTemplate, timeout(1)).postForEntity(anyString(), any(), any(), anyMap());
    }

}