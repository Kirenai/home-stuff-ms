package me.kirenai.re.consumption.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.consumption.dto.NourishmentRequest;
import me.kirenai.re.consumption.dto.NourishmentResponse;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class NourishmentManager {

    private static final String NOURISHMENT_GET_ONE_URL = "http://NOURISHMENT/api/v0/nourishments/{nourishmentId}";
    private static final String NOURISHMENT_PUT_URL = "http://NOURISHMENT/api/v0/nourishments/{nourishmentId}";

    private final WebClient.Builder webClient;
    private final JwtTokenProvider jwtTokenProvider;

    public Mono<NourishmentResponse> findNourishment(Long nourishmentId, String token) {
        log.info("Invoking NourishmentManager.findNourishment method");
        log.info("Call nourishment service findOne");
        return this.webClient.build()
                .get()
                .uri(NOURISHMENT_GET_ONE_URL, nourishmentId)
                .header(this.jwtTokenProvider.getAuthorizationHeader(), token)
                .retrieve()
                .bodyToMono(NourishmentResponse.class);
    }

    public Mono<Void> updateNourishment(NourishmentRequest nourishmentRequest, NourishmentResponse nourishmentResponse,
                                        String token) {
        log.info("Invoking NourishmentManager.updateNourishment method");
        log.info("Call nourishment service update");
        return this.webClient.build()
                .put()
                .uri(NOURISHMENT_PUT_URL, nourishmentResponse.getNourishmentId())
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(nourishmentRequest), NourishmentRequest.class)
                .header(this.jwtTokenProvider.getAuthorizationHeader(), token)
                .retrieve()
                .bodyToMono(Void.class);
    }

}