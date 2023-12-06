package me.kirenai.re.nourishment.router;

import me.kirenai.re.nourishment.dto.NourishmentRequest;
import me.kirenai.re.nourishment.dto.NourishmentResponse;
import me.kirenai.re.nourishment.handler.NourishmentHandler;
import me.kirenai.re.nourishment.service.NourishmentService;
import me.kirenai.re.nourishment.util.NourishmentMocks;
import me.kirenai.re.security.validator.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@WebFluxTest
@ContextConfiguration(classes = {NourishmentRouter.class, NourishmentHandler.class})
class NourishmentRouterTest {
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private NourishmentService nourishmentService;
    @MockBean
    private Validator validator;

    private final StringBuilder URL = new StringBuilder("/api/v0/nourishments");

    @Test
    @DisplayName("Should get one nourishment")
    void shouldGetOneNourishment() {
        NourishmentResponse nourishmentResponse = NourishmentMocks.getNourishmentResponse();
        when(this.nourishmentService.findOne(anyLong())).thenReturn(Mono.just(nourishmentResponse));

        this.webTestClient.get()
                .uri(URL.append("/1").toString())
                .exchange()
                .expectStatus().isOk()
                .expectBody(NourishmentResponse.class)
                .value(NourishmentResponse::getNourishmentId, equalTo(nourishmentResponse.getNourishmentId()))
                .value(NourishmentResponse::getName, equalTo(nourishmentResponse.getName()))
                .value(NourishmentResponse::getImageUrl, equalTo(nourishmentResponse.getImageUrl()))
                .value(NourishmentResponse::getDescription, equalTo(nourishmentResponse.getDescription()))
                .value(NourishmentResponse::getIsAvailable, equalTo(nourishmentResponse.getIsAvailable()))
                .value(NourishmentResponse::getUnit, equalTo(nourishmentResponse.getUnit()));
    }

    @Test
    @DisplayName("Should find roles by user id")
    void shouldFindRolesByUserId() {
        when(this.nourishmentService.findAllByUserId(anyLong()))
                .thenReturn(Flux.just(NourishmentMocks.getNourishmentResponse()));

        this.webTestClient.get()
                .uri(URL.append("/user/1").toString())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(NourishmentResponse.class)
                .hasSize(1);
    }

    @Test
    @DisplayName("Should find roles by isAvailable")
    void shouldFindRolesByIsAvailable() {
        when(this.nourishmentService.findAllByIsAvailable(anyBoolean()))
                .thenReturn(Flux.just(NourishmentMocks.getNourishmentResponse()));

        this.webTestClient.get()
                .uri(URL.append("/isAvailable/true").toString())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(NourishmentResponse.class)
                .hasSize(1);
    }

    @Test
    @DisplayName("Should save a nourishment")
    void shouldSaveNourishment() {
        NourishmentResponse nourishmentResponse = NourishmentMocks.getNourishmentResponse();
        when(this.nourishmentService.create(anyLong(), anyLong(), any()))
                .thenReturn(Mono.just(nourishmentResponse));

        this.webTestClient
                .post()
                .uri(URL.append("/user/1/category/1").toString())
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .body(Mono.just(NourishmentMocks.getNourishmentRequest()), NourishmentRequest.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(NourishmentResponse.class)
                .value(NourishmentResponse::getNourishmentId, equalTo(nourishmentResponse.getNourishmentId()))
                .value(NourishmentResponse::getName, equalTo(nourishmentResponse.getName()))
                .value(NourishmentResponse::getImageUrl, equalTo(nourishmentResponse.getImageUrl()))
                .value(NourishmentResponse::getDescription, equalTo(nourishmentResponse.getDescription()))
                .value(NourishmentResponse::getIsAvailable, equalTo(nourishmentResponse.getIsAvailable()))
                .value(NourishmentResponse::getPercentage, equalTo(nourishmentResponse.getPercentage()));
    }

    @Test
    @DisplayName("Should update a nourishment")
    void shouldUpdateNourishment() {
        NourishmentResponse nourishmentResponse = NourishmentMocks.getNourishmentResponse();
        when(this.nourishmentService.update(anyLong(), any()))
                .thenReturn(Mono.just(nourishmentResponse));

        this.webTestClient
                .put()
                .uri(URL.append("/1").toString())
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .body(Mono.just(NourishmentMocks.getNourishmentRequest()), NourishmentRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(NourishmentResponse.class)
                .value(NourishmentResponse::getNourishmentId, equalTo(nourishmentResponse.getNourishmentId()))
                .value(NourishmentResponse::getName, equalTo(nourishmentResponse.getName()))
                .value(NourishmentResponse::getImageUrl, equalTo(nourishmentResponse.getImageUrl()))
                .value(NourishmentResponse::getDescription, equalTo(nourishmentResponse.getDescription()))
                .value(NourishmentResponse::getIsAvailable, equalTo(nourishmentResponse.getIsAvailable()))
                .value(NourishmentResponse::getPercentage, equalTo(nourishmentResponse.getPercentage()));
    }

    @Test
    @DisplayName("Should delete a nourishment")
    void shouldDeleteNourishment() {
        when(this.nourishmentService.delete(anyLong())).thenReturn(Mono.empty());

        this.webTestClient
                .delete()
                .uri(URL.append("/1").toString())
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .exchange()
                .expectStatus().isOk();
    }

}