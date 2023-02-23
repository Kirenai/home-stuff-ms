package me.kirenai.re.category.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kirenai.re.category.dto.CategoryResponse;
import me.kirenai.re.category.mock.CategoryMocks;
import me.kirenai.re.category.service.CategoryService;
import me.kirenai.re.security.jwt.JwtTokenFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CategoryController.class,
        excludeFilters = @ComponentScan.Filter(classes = {JwtTokenFilter.class}, type = FilterType.ASSIGNABLE_TYPE))
@WithMockUser
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CategoryService categoryService;

    private final StringBuilder URL = new StringBuilder("/api/v0/categories");

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
                .with(csrf())
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
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(CategoryMocks.getCategoryRequest()))
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(this.objectMapper.writeValueAsString(categoryResponse)));
    }

}