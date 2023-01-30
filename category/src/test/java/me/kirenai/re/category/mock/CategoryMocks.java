package me.kirenai.re.category.mock;

import me.kirenai.re.category.dto.CategoryRequest;
import me.kirenai.re.category.dto.CategoryResponse;
import me.kirenai.re.category.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;
import java.util.List;

public class CategoryMocks {

    public static CategoryResponse getCategoryResponse() {
        return getCategoryResponseList().get(0);
    }

    public static List<CategoryResponse> getCategoryResponseList() {
        return Arrays.asList(
                getCategoryResponseMock(1L, "name1"),
                getCategoryResponseMock(2L, "name2"),
                getCategoryResponseMock(3L, "name3")
        );
    }

    private static CategoryResponse getCategoryResponseMock(Long categoryId, String name) {
        return CategoryResponse.builder()
                .categoryId(categoryId)
                .name(name)
                .build();
    }

    public static CategoryRequest getCategoryRequest() {
        return CategoryRequest.builder()
                .name("name1")
                .build();
    }

    public static Category getCategory() {
        return Category.builder()
                .categoryId(1L)
                .name("name1")
                .build();
    }

    public static Page<Category> getCategoryPage() {
        return new PageImpl<>(Arrays.asList(
                getCategoryMock(1L, "name1"),
                getCategoryMock(2L, "name2"),
                getCategoryMock(3L, "name3")
        ));
    }

    private static Category getCategoryMock(Long categoryId, String name) {
        return Category.builder()
                .categoryId(categoryId)
                .name(name)
                .build();
    }
}