package me.kirenai.re.nourishment.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.nourishment.dto.NourishmentRequest;
import me.kirenai.re.nourishment.service.NourishmentService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class NourishmentHandler {

    private final NourishmentService nourishmentService;

    public Mono<ServerResponse> findOne(ServerRequest request) {
        String nourishmentId = request.pathVariable("nourishmentId");
        return this.nourishmentService.findOne(Long.valueOf(nourishmentId))
                .flatMap(nourishment -> ServerResponse.ok().bodyValue(nourishment));
    }

    public Mono<ServerResponse> findAllByUserId(ServerRequest request) {
        String userId = request.pathVariable("userId");
        return this.nourishmentService.findAllByUserId(Long.valueOf(userId))
                .collectList()
                .flatMap(nourishments -> ServerResponse.ok().bodyValue(nourishments));
    }

    public Mono<ServerResponse> findAllByIsAvailable(ServerRequest request) {
        String isAvailable = request.pathVariable("isAvailable");
        return this.nourishmentService.findAllByIsAvailable(Boolean.valueOf(isAvailable))
                .collectList()
                .flatMap(nourishments -> ServerResponse.ok().bodyValue(nourishments));
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        String userId = request.pathVariable("userId");
        String categoryId = request.pathVariable("categoryId");
        String token = request.headers().firstHeader(HttpHeaders.AUTHORIZATION);
        return request.bodyToMono(NourishmentRequest.class)
                .flatMap(nourishmentRequest -> this.nourishmentService.create(
                        Long.valueOf(userId), Long.valueOf(categoryId), nourishmentRequest, token)
                )
                .flatMap(nourishment -> ServerResponse.status(HttpStatus.CREATED).bodyValue(nourishment));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String nourishmentId = request.pathVariable("nourishmentId");
        return request.bodyToMono(NourishmentRequest.class)
                .flatMap(nourishmentRequest -> this.nourishmentService.update(Long.valueOf(nourishmentId), nourishmentRequest))
                .flatMap(nourishment -> ServerResponse.ok().bodyValue(nourishment));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String nourishmentId = request.pathVariable("nourishmentId");
        return this.nourishmentService.delete(Long.valueOf(nourishmentId))
                .flatMap(nourishment -> ServerResponse.ok().bodyValue(Mono.empty()));
    }

}