package me.kirenai.re.nourishment.util;

import me.kirenai.re.nourishment.util.helper.ObjectMapperHelper;
import me.kirenai.re.nourishment.dto.CategoryResponse;

import java.io.IOException;

public class CategoryMocks {

    private static final CategoryMocks INSTANCE = new CategoryMocks();

    private final ObjectMapperHelper mapper = new ObjectMapperHelper();

    public static CategoryMocks getInstance() {
        return INSTANCE;
    }

    public CategoryResponse getCategoryResponse() throws IOException {
        return this.mapper.readValue(
                Thread.currentThread().getContextClassLoader().getResourceAsStream("mock/category/CategoryResponse.json"),
                CategoryResponse.class
        );
    }

}