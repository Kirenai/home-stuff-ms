package me.kirenai.re.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.auth.dto.ApiResponse;
import me.kirenai.re.auth.dto.LoginRequest;
import me.kirenai.re.auth.dto.RegisterRequest;
import me.kirenai.re.auth.dto.UserResponse;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    public static final String POST_USER_URL = "http://USER/api/v0/users";
    private final AuthenticationManager authenticationManager;
    private final RestTemplate restTemplate;
    private final JwtTokenProvider jwtTokenProvider;

    public Authentication login(LoginRequest loginRequest) {
        log.info("Invoking AuthService.login method");
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    public ApiResponse<UserResponse> register(RegisterRequest registerRequest) {
        log.info("Invoking AuthService.register method");
        String token = this.jwtTokenProvider.generateInternalJwtToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, this.jwtTokenProvider.getTokenPrefix() + token);
        ResponseEntity<ApiResponse> response = this.restTemplate.exchange(
                POST_USER_URL,
                HttpMethod.POST,
                new HttpEntity<>(registerRequest, headers),
                ApiResponse.class
        );
        return response.getBody();
    }

}