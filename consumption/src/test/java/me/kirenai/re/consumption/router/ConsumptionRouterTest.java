package me.kirenai.re.consumption.router;

import me.kirenai.re.consumption.dto.ConsumptionRequest;
import me.kirenai.re.consumption.handler.ConsumptionHandler;
import me.kirenai.re.consumption.service.ConsumptionService;
import me.kirenai.re.consumption.util.ConsumptionMocks;
import me.kirenai.re.security.jwt.JwtTokenFilter;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@WithMockUser
@WebFluxTest(excludeFilters = @ComponentScan.Filter(classes = {JwtTokenFilter.class}, type = FilterType.ASSIGNABLE_TYPE))
@ContextConfiguration(classes = {ConsumptionRouter.class, ConsumptionHandler.class})
class ConsumptionRouterTest {

    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private ConsumptionService consumptionService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    private final StringBuilder URL = new StringBuilder("/api/v0/consumptions");

    @Test
    @DisplayName("Should find one consumption")
    void shouldFindOneConsumption() {
        when(this.consumptionService.findOne(anyLong())).thenReturn(Mono.just(ConsumptionMocks.getConsumptionResponse()));

        this.webTestClient.get()
                .uri(URL.append("/1").toString())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("Should create a consumption")
    void shouldCreateConsumption() {
        when(this.consumptionService.create(anyLong(), anyLong(), any(), anyString()))
                .thenReturn(Mono.just(ConsumptionMocks.getConsumptionResponse()));

        this.webTestClient.mutateWith(SecurityMockServerConfigurers.csrf())
                .post()
                .uri(URL.append("/user/1/nourishment/1").toString())
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .body(Mono.just(ConsumptionMocks.getConsumptionRequest()), ConsumptionRequest.class)
                .exchange()
                .expectStatus().isCreated();
    }

}