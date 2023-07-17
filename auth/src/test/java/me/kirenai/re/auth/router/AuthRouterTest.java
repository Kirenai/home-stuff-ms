package me.kirenai.re.auth.router;

import me.kirenai.re.auth.dto.LoginRequest;
import me.kirenai.re.auth.dto.RegisterRequest;
import me.kirenai.re.auth.handler.AuthHandler;
import me.kirenai.re.auth.service.AuthService;
import me.kirenai.re.auth.util.AuthMocks;
import me.kirenai.re.security.jwt.JwtTokenFilter;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import me.kirenai.re.security.service.AuthUserDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WithMockUser
@WebFluxTest(excludeFilters = @ComponentScan.Filter(
        classes = {JwtTokenFilter.class}, type = FilterType.ASSIGNABLE_TYPE))
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
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                new AuthUserDetails(1L, "username", "password", List.of(new SimpleGrantedAuthority("ROLE_USER"))),
                "password");
        when(this.authService.login(any())).thenReturn(Mono.just(authentication));

        this.webTestClient.mutateWith(SecurityMockServerConfigurers.csrf())
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

        this.webTestClient.mutateWith(SecurityMockServerConfigurers.csrf())
                .post()
                .uri(URL.append("/register").toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(AuthMocks.getRegisterRequest()), RegisterRequest.class)
                .exchange()
                .expectStatus().isCreated();

    }

}