package me.kirenai.re.nourishment.util;

import me.kirenai.re.nourishment.dto.CategoryResponse;
import org.springframework.http.ResponseEntity;

public class CategoryMocks {

    public static CategoryResponse categoryMock(Long categoryId, String name) {
        return CategoryResponse.builder()
                .categoryId(categoryId)
                .name(name)
                .build();
    }

    public static CategoryResponse getCategoryResponse() {
        return categoryMock(1L, "category1");
    }

    public static ResponseEntity<CategoryResponse> getCategoryResponseEntity() {
        return ResponseEntity.ok(getCategoryResponse());
    }

}