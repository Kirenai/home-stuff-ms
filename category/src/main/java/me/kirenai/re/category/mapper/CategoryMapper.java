package me.kirenai.re.category.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.category.dto.CategoryRequest;
import me.kirenai.re.category.dto.CategoryResponse;
import me.kirenai.re.category.entity.Category;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryMapper {

    private final ModelMapper modelMapper;

    public CategoryResponse mapOutCategoryToCategoryResponse(Category category) {
        log.info("Invoking CategoryMapper.mapOutCategoryToCategoryResponse method");
        return this.modelMapper.map(category, CategoryResponse.class);
    }

    public Category mapOutCategoryRequestToCategory(CategoryRequest categoryRequest) {
        log.info("Invoking CategoryMapper.mapOutCategoryRequestToCategory method");
        return this.modelMapper.map(categoryRequest, Category.class);
    }

}