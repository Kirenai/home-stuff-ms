package me.kirenai.re.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.category.dto.CategoryRequest;
import me.kirenai.re.category.dto.CategoryResponse;
import me.kirenai.re.category.service.CategoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategories(
            @PageableDefault(size = 7)
            @SortDefault.SortDefaults(value = {
                    @SortDefault(sort = "categoryId", direction = Sort.Direction.ASC)
            }) Pageable pageable
    ) {
        log.info("Invoking CategoryController.getCategories method");
        List<CategoryResponse> response = this.categoryService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("{categoryId}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable Long categoryId) {
        log.info("Invoking CategoryController.getCategory method");
        CategoryResponse response = this.categoryService.findOne(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {
        log.info("Invoking CategoryController.createCategory method");
        CategoryResponse response = this.categoryService.create(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long categoryId,
                                                           @RequestBody CategoryRequest categoryRequest) {
        log.info("Invoking CategoryController.updateCategory method");
        CategoryResponse response = this.categoryService.update(categoryId, categoryRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("{categoryId}")
    public ResponseEntity<CategoryResponse> deleteCategory(@PathVariable Long categoryId) {
        log.info("Invoking CategoryController.deleteCategory method");
        this.categoryService.delete(categoryId);
        return ResponseEntity.noContent().build();
    }

}