package me.kirenai.re.consumption.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.consumption.dto.ConsumptionRequest;
import me.kirenai.re.consumption.service.ConsumptionService;
import me.kirenai.re.security.dto.ErrorResponse;
import me.kirenai.re.security.validator.Validator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConsumptionHandler {

    private final ConsumptionService consumptionService;
    private final Validator validator;

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
                .flatMap(consumptionRequest -> {
                    List<ErrorResponse> errors = this.validator.validate(consumptionRequest);
                    if (!errors.isEmpty()) {
                        return ServerResponse.badRequest().bodyValue(errors);
                    }
                    return this.consumptionService.create(
                                    Long.valueOf(nourishmentId),
                                    Long.valueOf(userId),
                                    consumptionRequest,
                                    token
                            )
                            .flatMap(consumptionResponse -> ServerResponse.status(HttpStatus.CREATED)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .bodyValue(consumptionResponse)
                            );
                });
    }

}
