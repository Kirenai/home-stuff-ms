package me.kirenai.re.auth.router;

import me.kirenai.re.auth.dto.LoginRequest;
import me.kirenai.re.auth.dto.RegisterRequest;
import me.kirenai.re.auth.handler.AuthHandler;
import me.kirenai.re.auth.service.AuthService;
import me.kirenai.re.auth.util.AuthMocks;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest
@ContextConfiguration(classes = {AuthRouter.class, AuthHandler.class})
class AuthRouterTest {

    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private AuthService authService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    private final StringBuilder URL = new StringBuilder("/api/v0/auth");

    @Test
    public void loginTest() {
        when(this.authService.login(any())).thenReturn(Mono.just("token"));

        this.webTestClient
                .post()
                .uri(URL.append("/login").toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(AuthMocks.getLoginRequest()), LoginRequest.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void registerTest() throws IOException {
        when(this.authService.register(any())).thenReturn(Mono.just(AuthMocks.getUserResponse()));

        this.webTestClient
                .post()
                .uri(URL.append("/register").toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(AuthMocks.getRegisterRequest()), RegisterRequest.class)
                .exchange()
                .expectStatus().isCreated();
    }

}