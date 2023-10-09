package me.kirenai.re.category.router;

import me.kirenai.re.category.dto.CategoryRequest;
import me.kirenai.re.category.dto.CategoryResponse;
import me.kirenai.re.category.handler.CategoryHandler;
import me.kirenai.re.category.mock.CategoryMocks;
import me.kirenai.re.category.service.CategoryService;
import me.kirenai.re.security.jwt.JwtTokenFilter;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import me.kirenai.re.security.validator.GlobalValidator;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@WithMockUser
@WebFluxTest(excludeFilters = @ComponentScan.Filter(classes = {JwtTokenFilter.class}, type = FilterType.ASSIGNABLE_TYPE))
@ContextConfiguration(classes = {CategoryRouter.class, CategoryHandler.class})
class CategoryRouterTest {

    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private CategoryService categoryService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private GlobalValidator validator;

    private final StringBuilder URL = new StringBuilder("/api/v0/categories");

    @Test
    @DisplayName("Should find a category")
    public void shouldFindCategory() {
        CategoryResponse categoryResponse = CategoryMocks.getCategoryResponse();
        when(this.categoryService.findOne(anyLong())).thenReturn(Mono.just(categoryResponse));

        this.webTestClient
                .get()
                .uri(URL.append("/1").toString())
                .exchange()
                .expectStatus().isOk()
                .expectBody(CategoryResponse.class)
                .value(CategoryResponse::getCategoryId, Matchers.equalTo(categoryResponse.getCategoryId()))
                .value(CategoryResponse::getName, Matchers.equalTo(categoryResponse.getName()));
    }

    @Test
    @DisplayName("Should create a category")
    public void shouldCreateCategory() {
        CategoryResponse categoryResponse = CategoryMocks.getCategoryResponse();
        when(this.categoryService.create(any())).thenReturn(Mono.just(categoryResponse));

        this.webTestClient
                .mutateWith(SecurityMockServerConfigurers.csrf())
                .post()
                .uri(URL.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(CategoryMocks.getCategoryRequest()), CategoryRequest.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CategoryResponse.class)
                .value(CategoryResponse::getCategoryId, Matchers.equalTo(categoryResponse.getCategoryId()))
                .value(CategoryResponse::getName, Matchers.equalTo(categoryResponse.getName()));
    }

    @Test
    @DisplayName("Should update a category")
    public void shouldUpdateCategory() {
        CategoryResponse categoryResponse = CategoryMocks.getCategoryResponse();
        when(this.categoryService.update(anyLong(), any())).thenReturn(Mono.just(categoryResponse));

        this.webTestClient
                .mutateWith(SecurityMockServerConfigurers.csrf())
                .put()
                .uri(URL.append("/1").toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(CategoryMocks.getCategoryRequest()), CategoryRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CategoryResponse.class)
                .value(CategoryResponse::getCategoryId, Matchers.equalTo(categoryResponse.getCategoryId()))
                .value(CategoryResponse::getName, Matchers.equalTo(categoryResponse.getName()));
    }

    @Test
    @DisplayName("Should delete a category")
    public void shouldDeleteCategory() {
        when(this.categoryService.delete(anyLong())).thenReturn(Mono.empty());

        this.webTestClient
                .mutateWith(SecurityMockServerConfigurers.csrf())
                .delete()
                .uri(URL.append("/1").toString())
                .exchange()
                .expectStatus().isOk();
    }

}