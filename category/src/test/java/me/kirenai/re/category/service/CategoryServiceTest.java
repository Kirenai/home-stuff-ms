package me.kirenai.re.category.service;

import me.kirenai.re.category.dto.CategoryResponse;
import me.kirenai.re.category.mapper.CategoryMapper;
import me.kirenai.re.category.mock.CategoryMocks;
import me.kirenai.re.category.repository.CategoryRepository;
import me.kirenai.re.exception.category.CategoryNotFoundExceptionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper mapper;

//    @Test
//    @DisplayName("Should return all categories")
//    void findAllTest() {
//        List<CategoryResponse> categoryResponseList = CategoryMocks.getCategoryResponseList();
//
//        when(this.categoryRepository.findAll(any(Pageable.class)))
//                .thenReturn(CategoryMocks.getCategoryPage());
//        when(this.mapper.mapOutCategoryToCategoryResponse(any()))
//                .thenReturn(categoryResponseList.get(0), categoryResponseList.get(1), categoryResponseList.get(2));
//
//        List<CategoryResponse> response = this.categoryService.findAll(mock(Pageable.class));
//
//        assertEquals(categoryResponseList, response);
//
//        verify(this.categoryRepository, times(1)).findAll(any(Pageable.class));
//        verify(this.mapper, times(3)).mapOutCategoryToCategoryResponse(any());
//    }

    @Test
    @DisplayName("Should return a category")
    void findOneTest() {
        CategoryResponse categoryResponse = CategoryMocks.getCategoryResponse();

        when(this.categoryRepository.findById(anyLong())).thenReturn(Mono.just(CategoryMocks.getCategory()));
        when(this.mapper.mapOutCategoryToCategoryResponse(any())).thenReturn(categoryResponse);

        Mono<CategoryResponse> response = this.categoryService.findOne(1L);

        StepVerifier
                .create(response)
                .expectNext(categoryResponse)
                .verifyComplete();

        verify(this.categoryRepository, times(1)).findById(anyLong());
        verify(this.mapper, times(1)).mapOutCategoryToCategoryResponse(any());
    }

    @Test
    @DisplayName("Should throw category not found exception when GET")
    void shouldThrowCategoryNotFoundExceptionWhen_GET() {
        when(this.categoryRepository.findById(anyLong())).thenReturn(Mono.empty());

        Mono<CategoryResponse> response = this.categoryService.findOne(1L);

        StepVerifier
                .create(response)
                .expectError(CategoryNotFoundExceptionFactory.class)
                .verify();

        verify(this.categoryRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Should create a category")
    void createTest() {
        CategoryResponse categoryResponse = CategoryMocks.getCategoryResponse();

        when(this.mapper.mapInCategoryRequestToCategory(any())).thenReturn(CategoryMocks.getCategory());
        when(this.categoryRepository.save(any())).thenReturn(Mono.just(CategoryMocks.getCategory()));
        when(this.mapper.mapOutCategoryToCategoryResponse(any())).thenReturn(categoryResponse);

        Mono<CategoryResponse> response = this.categoryService.create(CategoryMocks.getCategoryRequest());

        StepVerifier
                .create(response)
                .expectNext(categoryResponse)
                .verifyComplete();

        verify(this.mapper, Mockito.times(1)).mapInCategoryRequestToCategory(any());
        verify(this.mapper, Mockito.times(1)).mapOutCategoryToCategoryResponse(any());
    }

    @Test
    @DisplayName("Should update a category")
    void updateTest() {
        CategoryResponse categoryResponse = CategoryMocks.getCategoryResponse();

        when(this.categoryRepository.findById(anyLong())).thenReturn(Mono.just(CategoryMocks.getCategory()));
        when(this.categoryRepository.save(any())).thenReturn(Mono.just(CategoryMocks.getCategory()));
        when(this.mapper.mapOutCategoryToCategoryResponse(any())).thenReturn(categoryResponse);

        Mono<CategoryResponse> response = this.categoryService.update(1L, CategoryMocks.getCategoryRequest());

        StepVerifier
                .create(response)
                .expectNext(categoryResponse)
                .verifyComplete();

        verify(this.categoryRepository, times(1)).findById(anyLong());
        verify(this.mapper, times(1)).mapOutCategoryToCategoryResponse(any());
    }

    @Test
    @DisplayName("Should throw a category not found exception when PUT")
    void shouldThrowCategoryNotFoundExceptionWhen_PUT() {
        when(this.categoryRepository.findById(anyLong())).thenReturn(Mono.empty());

        Mono<CategoryResponse> response = this.categoryService.update(1L, CategoryMocks.getCategoryRequest());

        StepVerifier
                .create(response)
                .expectError(CategoryNotFoundExceptionFactory.class)
                .verify();

        verify(this.categoryRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Should delete a category")
    void deleteTest() {
        when(this.categoryRepository.findById(anyLong())).thenReturn(Mono.just(CategoryMocks.getCategory()));
        when(this.categoryRepository.delete(any())).thenReturn(Mono.empty());

        Mono<Void> response = this.categoryService.delete(1L);

        StepVerifier
                .create(response)
                .verifyComplete();

        verify(this.categoryRepository, times(1)).findById(anyLong());
        verify(this.categoryRepository, times(1)).delete(any());
    }

    @Test
    @DisplayName("Should throw category not found exception when DELETE")
    void shouldThrowCategoryNotFoundExceptionWhen_DELETE() {
        when(this.categoryRepository.findById(anyLong())).thenReturn(Mono.empty());

        Mono<Void> response = this.categoryService.delete(1L);

        StepVerifier
                .create(response)
                .expectError(CategoryNotFoundExceptionFactory.class)
                .verify();

        verify(this.categoryRepository, times(1)).findById(anyLong());
    }

}