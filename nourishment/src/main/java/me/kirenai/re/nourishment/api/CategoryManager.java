package me.kirenai.re.nourishment.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.nourishment.dto.CategoryResponse;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryManager {

    private static final String CATEGORY_URL_GET_ONE = "http://CATEGORY/api/v0/categories/{categoryId}";

    private final RestTemplate restTemplate;
    private final JwtTokenProvider jwtTokenProvider;

    public CategoryResponse findCategory(Long categoryId) {
        log.info("Invoking CategoryManager.findCategory method");
        log.info("Call category service");
        return this.restTemplate.exchange(
                CATEGORY_URL_GET_ONE,
                HttpMethod.GET,
                new HttpEntity<>(this.jwtTokenProvider.getCurrentTokenAsHeader()),
                CategoryResponse.class,
                categoryId
        ).getBody();
    }

}
