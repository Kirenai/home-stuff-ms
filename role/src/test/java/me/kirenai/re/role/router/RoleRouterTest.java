package me.kirenai.re.role.router;

import me.kirenai.re.role.dto.RoleRequest;
import me.kirenai.re.role.dto.RoleResponse;
import me.kirenai.re.role.handler.RoleHandler;
import me.kirenai.re.role.service.RoleService;
import me.kirenai.re.role.util.RoleMocks;
import me.kirenai.re.security.jwt.JwtTokenFilter;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@WithMockUser
@WebFluxTest(excludeFilters = @ComponentScan.Filter(
        classes = {JwtTokenFilter.class}, type = FilterType.ASSIGNABLE_TYPE))
@ContextConfiguration(classes = {RoleRouter.class, RoleHandler.class})
class RoleRouterTest {

    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private RoleService roleService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    private final StringBuilder URL = new StringBuilder("/api/v0/roles");

    @Test
    @DisplayName("Should get role")
    void shouldGetRoleTesT() {
        when(this.roleService.findOne(anyLong())).thenReturn(Mono.just(RoleMocks.getRoleResponse()));

        this.webTestClient
                .get()
                .uri(URL.append("/1").toString())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("Should get roles by user id")
    void shouldGetAllRolesTest() {
        List<RoleResponse> roleResponseList = RoleMocks.getRoleResponseList();
        when(this.roleService.findAllByUserId(anyLong())).thenReturn(Flux.fromIterable(roleResponseList));

        this.webTestClient
                .get()
                .uri(URL.append("/user/1").toString())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(RoleResponse.class)
                .hasSize(roleResponseList.size());
    }

    @Test
    @DisplayName("Should save a role")
    void shouldSaveRole() {
        RoleResponse roleResponse = RoleMocks.getRoleResponse();
        when(this.roleService.create(any())).thenReturn(Mono.just(roleResponse));

        this.webTestClient
                .mutateWith(SecurityMockServerConfigurers.csrf())
                .post()
                .uri(URL.toString())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(RoleMocks.getRoleRequest()), RoleRequest.class)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(RoleResponse.class)
                .value(RoleResponse::getRoleId, Matchers.equalTo(roleResponse.getRoleId()))
                .value(RoleResponse::getName, Matchers.equalTo(roleResponse.getName()));
    }

    @Test
    @DisplayName("Should save a role user")
    void shouldSaveRoleUser() {
        when(this.roleService.createRoleUser(anyLong())).thenReturn(Mono.empty());

        this.webTestClient
                .mutateWith(SecurityMockServerConfigurers.csrf())
                .post()
                .uri(URL.append("/user/1").toString())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("Should update a role user")
    void shouldUpdateRole() {
        RoleResponse roleResponse = RoleMocks.getRoleResponse();
        when(this.roleService.update(anyLong(), any())).thenReturn(Mono.just(roleResponse));

        this.webTestClient
                .mutateWith(SecurityMockServerConfigurers.csrf())
                .put()
                .uri(URL.append("/1").toString())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(RoleMocks.getRoleRequest()), RoleRequest.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(RoleResponse.class)
                .value(RoleResponse::getRoleId, Matchers.equalTo(roleResponse.getRoleId()))
                .value(RoleResponse::getName, Matchers.equalTo(roleResponse.getName()));
    }

}