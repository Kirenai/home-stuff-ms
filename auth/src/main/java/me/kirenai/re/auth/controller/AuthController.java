package me.kirenai.re.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.auth.dto.ApiResponse;
import me.kirenai.re.auth.dto.LoginRequest;
import me.kirenai.re.auth.dto.RegisterRequest;
import me.kirenai.re.auth.dto.UserResponse;
import me.kirenai.re.auth.service.AuthService;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import me.kirenai.re.security.service.AuthUserDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<AuthUserDetails> login(@RequestBody @Valid LoginRequest loginRequest) {
        log.info("Invoking AuthController.login method");
        Authentication authentication = authService.login(loginRequest);
        AuthUserDetails principal = (AuthUserDetails) authentication.getPrincipal();
        String token = this.jwtTokenProvider.generateJwtToken(authentication);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, this.jwtTokenProvider.getTokenPrefix() + token)
                .body(principal);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@RequestBody @Valid RegisterRequest registerRequest) {
        log.info("Invoking AuthController.register method");
        ApiResponse<UserResponse> response = this.authService.register(registerRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

}