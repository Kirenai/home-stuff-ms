package me.kirenai.re.category.mapper;

import me.kirenai.re.category.dto.CategoryRequest;
import me.kirenai.re.category.dto.CategoryResponse;
import me.kirenai.re.category.entity.Category;
import me.kirenai.re.category.mock.CategoryMocks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

    private final CategoryMapper mapper = Mappers.getMapper(CategoryMapper.class);

    @Test
    @DisplayName("mapInTest")
    void mapInCategoryRequestToCategoryTest() {
        CategoryRequest input = CategoryMocks.getCategoryRequest();
        Category request = this.mapper.mapInCategoryRequestToCategory(input);

        assertNotNull(request);
        assertNotNull(request.getName());

        assertEquals(input.getName(), request.getName());
    }

    @Test
    @DisplayName("mapOutTest")
    void mapOutCategoryToCategoryResponseTest() {
        Category input = CategoryMocks.getCategory();
        CategoryResponse response = this.mapper.mapOutCategoryToCategoryResponse(input);

        assertNotNull(response);
        assertNotNull(response.getCategoryId());
        assertNotNull(response.getName());

        assertEquals(input.getCategoryId(), response.getCategoryId());
        assertEquals(input.getName(), response.getName());
    }

}