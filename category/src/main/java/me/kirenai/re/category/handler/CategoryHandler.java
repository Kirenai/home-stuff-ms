package me.kirenai.re.category.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.category.dto.CategoryRequest;
import me.kirenai.re.category.service.CategoryService;
import me.kirenai.re.security.dto.ErrorResponse;
import me.kirenai.re.security.validator.Validator;
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
public class CategoryHandler {

    private final CategoryService categoryService;
    private final Validator validator;

    public Mono<ServerResponse> findOne(ServerRequest request) {
        log.info("Invoking CategoryHandler.findOne method");
        String categoryId = request.pathVariable("categoryId");
        return this.categoryService.findOne(Long.valueOf(categoryId))
                .flatMap(category -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(category));
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        log.info("Invoking CategoryHandler.create method");
        return request.bodyToMono(CategoryRequest.class)
                .flatMap(categoryRequest -> {
                    List<ErrorResponse> errors = this.validator.validate(categoryRequest);
                    if (!errors.isEmpty()) {
                        return ServerResponse.badRequest().bodyValue(errors);
                    }
                    return this.categoryService.create(categoryRequest)
                            .flatMap(category -> ServerResponse.status(HttpStatus.CREATED)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .bodyValue(category)
                            );
                });
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        log.info("Invoking CategoryHandler.update method");
        String categoryId = request.pathVariable("categoryId");
        return request.bodyToMono(CategoryRequest.class)
                .flatMap(categoryRequest -> {
                    List<ErrorResponse> errors = this.validator.validate(categoryRequest);
                    if (!errors.isEmpty()) {
                        return ServerResponse.badRequest().bodyValue(errors);
                    }
                    return this.categoryService.update(Long.valueOf(categoryId), categoryRequest)
                            .flatMap(category -> ServerResponse.ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .bodyValue(category)
                            );
                });
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        log.info("Invoking CategoryHandler.delete method");
        String categoryId = request.pathVariable("categoryId");
        return this.categoryService.delete(Long.valueOf(categoryId))
                .flatMap(category -> ServerResponse.noContent().build());
    }

}
