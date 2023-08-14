package me.kirenai.re.user.router;

import me.kirenai.re.security.jwt.JwtTokenFilter;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import me.kirenai.re.user.dto.UserRequest;
import me.kirenai.re.user.dto.UserResponse;
import me.kirenai.re.user.handler.UserHandler;
import me.kirenai.re.user.service.UserService;
import me.kirenai.re.user.util.UserMocks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@WithMockUser
@WebFluxTest(excludeFilters = @ComponentScan.Filter(
        classes = {JwtTokenFilter.class}, type = FilterType.ASSIGNABLE_TYPE))
@ContextConfiguration(classes = {UserRouter.class, UserHandler.class})
class UserRouterTest {

    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private UserService userService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    private final StringBuilder URL = new StringBuilder("/api/v0/users");

    @Test
    @DisplayName("Should get all users")
    void shouldGetAllUsers() {
        Flux<UserResponse> response = UserMocks.getFluxUserResponse();
        when(this.userService.findAll(any())).thenReturn(response);

        this.webTestClient
                .get()
                .uri(URL.toString())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(UserResponse.class)
                .hasSize(3);
    }

    @Test
    @DisplayName("Should get user")
    void shouldGetUserTest() {
        UserResponse response = UserMocks.getUserResponse();
        when(this.userService.findOne(anyLong()))
                .thenReturn(Mono.just(response));

        this.webTestClient
                .get()
                .uri(URL.append("/1").toString())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(UserResponse.class)
                .value(UserResponse::getUserId, equalTo(response.getUserId()))
                .value(UserResponse::getUsername, equalTo(response.getUsername()))
                .value(UserResponse::getFirstName, equalTo(response.getFirstName()))
                .value(UserResponse::getLastName, equalTo(response.getLastName()))
                .value(UserResponse::getAge, equalTo(response.getAge()));
    }

    @Test
    @DisplayName("Should create user")
    void shouldCreateUser() {
        UserResponse response = UserMocks.getUserResponse();
        when(this.userService.create(any(), anyString()))
                .thenReturn(Mono.just(response));

        this.webTestClient
                .mutateWith(SecurityMockServerConfigurers.csrf())
                .post()
                .uri(URL.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .body(Mono.just(UserMocks.getUserRequest()), UserRequest.class)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(UserResponse.class)
                .value(UserResponse::getUserId, equalTo(response.getUserId()))
                .value(UserResponse::getUsername, equalTo(response.getUsername()))
                .value(UserResponse::getFirstName, equalTo(response.getFirstName()))
                .value(UserResponse::getLastName, equalTo(response.getLastName()))
                .value(UserResponse::getAge, equalTo(response.getAge()));
    }

}