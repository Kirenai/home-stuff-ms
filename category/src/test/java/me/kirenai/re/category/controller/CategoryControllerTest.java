package me.kirenai.re.category.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kirenai.re.category.dto.CategoryResponse;
import me.kirenai.re.category.mock.CategoryMocks;
import me.kirenai.re.category.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CategoryService categoryService;

    private final StringBuilder URL = new StringBuilder("/api/categories");

    @Test
    @DisplayName("Should return all categories")
    void getCategoriesTest() throws Exception {
        List<CategoryResponse> categoryResponseList = CategoryMocks.getCategoryResponseList();
        when(this.categoryService.findAll(any(Pageable.class)))
                .thenReturn(categoryResponseList);

        RequestBuilder request = MockMvcRequestBuilders
                .get(this.URL.toString());

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(content().json(this.objectMapper.writeValueAsString(categoryResponseList)));
    }

    @Test
    @DisplayName("Should return category by id")
    void getCategoryTest() throws Exception {
        CategoryResponse categoryResponse = CategoryMocks.getCategoryResponse();
        when(this.categoryService.findOne(anyLong()))
                .thenReturn(categoryResponse);

        RequestBuilder request = MockMvcRequestBuilders
                .get(this.URL.append("/1").toString());

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(this.objectMapper.writeValueAsString(categoryResponse)));
    }

    @Test
    @DisplayName("Should create category")
    void createCategoryTest() throws Exception {
        CategoryResponse categoryResponse = CategoryMocks.getCategoryResponse();
        when(this.categoryService.create(any()))
                .thenReturn(categoryResponse);

        RequestBuilder request = MockMvcRequestBuilders
                .post(this.URL.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(CategoryMocks.getCategoryRequest()))
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json(this.objectMapper.writeValueAsString(categoryResponse)));
    }

    @Test
    @DisplayName("Should update category")
    void updateCategoryTest() throws Exception {
        CategoryResponse categoryResponse = CategoryMocks.getCategoryResponse();
        when(this.categoryService.update(anyLong(), any()))
                .thenReturn(categoryResponse);

        RequestBuilder request = MockMvcRequestBuilders
                .put(this.URL.append("/1").toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(CategoryMocks.getCategoryRequest()))
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(this.objectMapper.writeValueAsString(categoryResponse)));
    }

}