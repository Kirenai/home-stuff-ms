package me.kirenai.re.category.mapper;

import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.category.dto.CategoryRequest;
import me.kirenai.re.category.dto.CategoryResponse;
import me.kirenai.re.category.entity.Category;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;

@Slf4j
@Mapper(componentModel = "spring")
public abstract class CategoryMapper {

    public abstract Category mapInCategoryRequestToCategory(CategoryRequest categoryRequest);

    public abstract CategoryResponse mapOutCategoryToCategoryResponse(Category category);

    @BeforeMapping
    public void mapInLog(CategoryRequest categoryRequest) {
        log.info("Invoking CategoryMapper.mapInCategoryRequestToCategory method");
    }

    @BeforeMapping
    public void mapOutLog(Category category) {
        log.info("Invoking CategoryMapper.mapOutCategoryToCategoryResponse method");
    }

}