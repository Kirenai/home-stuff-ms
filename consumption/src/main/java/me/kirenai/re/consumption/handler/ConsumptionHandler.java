package me.kirenai.re.consumption.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.consumption.dto.ConsumptionRequest;
import me.kirenai.re.consumption.service.ConsumptionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConsumptionHandler {

    private final ConsumptionService consumptionService;

    public Mono<ServerResponse> findOne(ServerRequest request) {
        String consumptionId = request.pathVariable("consumptionId");
        return this.consumptionService.findOne(Long.valueOf(consumptionId))
                .flatMap(consumptionResponse -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(consumptionResponse)
                );
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        String nourishmentId = request.pathVariable("nourishmentId");
        String userId = request.pathVariable("userId");
        String token = request.headers().firstHeader(HttpHeaders.AUTHORIZATION);
        return request.bodyToMono(ConsumptionRequest.class)
                .flatMap(consumptionRequest -> this.consumptionService
                        .create(Long.valueOf(nourishmentId), Long.valueOf(userId), consumptionRequest, token)
                )
                .flatMap(consumptionResponse -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(consumptionResponse)
                );
    }

}
