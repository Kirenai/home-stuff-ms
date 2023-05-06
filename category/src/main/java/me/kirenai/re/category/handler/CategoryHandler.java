package me.kirenai.re.category.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.category.dto.CategoryRequest;
import me.kirenai.re.category.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryHandler {

    private final CategoryService categoryService;

    public Mono<ServerResponse> findOne(ServerRequest request) {
        log.info("Invoking CategoryHandler.findOne method");
        String categoryId = request.pathVariable("categoryId");
        return this.categoryService.findOne(Long.valueOf(categoryId))
                .flatMap(category -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(category));
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        log.info("Invoking CategoryHandler.create method");
        return request.bodyToMono(CategoryRequest.class)
                .flatMap(this.categoryService::create)
                .flatMap(category -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(category));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        log.info("Invoking CategoryHandler.update method");
        String categoryId = request.pathVariable("categoryId");
        return request.bodyToMono(CategoryRequest.class)
                .flatMap(categoryRequest -> this.categoryService.update(Long.valueOf(categoryId), categoryRequest))
                .flatMap(category -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(category));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        log.info("Invoking CategoryHandler.delete method");
        String categoryId = request.pathVariable("categoryId");
        return this.categoryService.delete(Long.valueOf(categoryId))
                .flatMap(category -> ServerResponse.noContent().build());
    }

}
