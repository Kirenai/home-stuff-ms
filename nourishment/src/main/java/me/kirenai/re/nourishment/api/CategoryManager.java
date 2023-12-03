package me.kirenai.re.nourishment.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.nourishment.dto.CategoryResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryManager {

    private static final String CATEGORY_URL_GET_ONE = "http://CATEGORY/api/v0/categories/{categoryId}";

    private final WebClient.Builder webClient;

    public Mono<CategoryResponse> findCategory(Long categoryId) {
        log.info("Invoking CategoryManager.findCategory method");
        log.info("Call category service");
        return this.webClient
                .build()
                .get()
                .uri(CATEGORY_URL_GET_ONE, categoryId)
                .retrieve()
                .bodyToMono(CategoryResponse.class);
    }

}
