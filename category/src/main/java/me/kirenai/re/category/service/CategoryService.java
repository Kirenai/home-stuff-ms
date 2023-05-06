package me.kirenai.re.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.category.dto.CategoryRequest;
import me.kirenai.re.category.dto.CategoryResponse;
import me.kirenai.re.category.entity.Category;
import me.kirenai.re.category.mapper.CategoryMapper;
import me.kirenai.re.category.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;

//    public List<CategoryResponse> findAll(Pageable pageable) {
//        log.info("Invoking CategoryService.findAll method");
//        return this.categoryRepository.findAll(pageable)
//                .getContent()
//                .stream()
//                .map(this.mapper::mapOutCategoryToCategoryResponse)
//                .toList();
//    }

    public Mono<CategoryResponse> findOne(Long categoryId) {
        log.info("Invoking CategoryService.findOne method");
        return this.categoryRepository.findById(categoryId)
                .map(this.mapper::mapOutCategoryToCategoryResponse);
    }

    public Mono<CategoryResponse> create(CategoryRequest categoryRequest) {
        log.info("Invoking CategoryService.create method");
        Category category = this.mapper.mapInCategoryRequestToCategory(categoryRequest);
        return this.categoryRepository.save(category)
                .map(this.mapper::mapOutCategoryToCategoryResponse);
    }

    public Mono<CategoryResponse> update(Long categoryId, CategoryRequest categoryRequest) {
        log.info("Invoking CategoryService.update method");
        return this.categoryRepository.findById(categoryId)
                .flatMap(category -> {
                    category.setName(categoryRequest.getName());
                    return this.categoryRepository.save(category);
                })
                .map(this.mapper::mapOutCategoryToCategoryResponse);
    }

    public Mono<Void> delete(Long categoryId) {
        log.info("Invoking CategoryService.delete method");
        return Mono.fromRunnable(() -> this.categoryRepository.deleteById(categoryId));
    }

}