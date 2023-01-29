package me.kirenai.re.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.category.dto.CategoryRequest;
import me.kirenai.re.category.dto.CategoryResponse;
import me.kirenai.re.category.entity.Category;
import me.kirenai.re.category.mapper.CategoryMapper;
import me.kirenai.re.category.repository.CategoryRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;

    public List<CategoryResponse> findAll(Pageable pageable) {
        log.info("Invoking CategoryService.findAll method");
        return this.categoryRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(this.mapper::mapOutCategoryToCategoryResponse)
                .toList();
    }

    public CategoryResponse findOne(Long categoryId) {
        log.info("Invoking CategoryService.findOne method");
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow();
        return this.mapper.mapOutCategoryToCategoryResponse(category);
    }

    public CategoryResponse create(CategoryRequest categoryRequest) {
        log.info("Invoking CategoryService.create method");
        Category category = this.mapper.mapInCategoryRequestToCategory(categoryRequest);
        this.categoryRepository.save(category);
        return this.mapper.mapOutCategoryToCategoryResponse(category);
    }

    public CategoryResponse update(Long categoryId, CategoryRequest categoryRequest) {
        log.info("Invoking CategoryService.update method");
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow();
        category.setName(categoryRequest.getName());
        this.categoryRepository.save(category);
        return this.mapper.mapOutCategoryToCategoryResponse(category);
    }

    public void delete(Long categoryId) {
        log.info("Invoking CategoryService.delete method");
        this.categoryRepository.deleteById(categoryId);
    }

}