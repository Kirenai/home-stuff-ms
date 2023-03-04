package me.kirenai.re.nourishment.api;

import me.kirenai.re.nourishment.dto.CategoryResponse;
import me.kirenai.re.nourishment.util.CategoryMocks;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryManagerTest {

    @InjectMocks
    private CategoryManager categoryManager;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("Should verify the category service call")
    public void findCategoryTest() {
        when(this.restTemplate.exchange(anyString(), any(), any(), eq(CategoryResponse.class), anyLong()))
                .thenReturn(CategoryMocks.getCategoryResponseEntity());
        this.categoryManager.findCategory(1L);
        verify(this.restTemplate, times(1)).exchange(anyString(), any(), any(), eq(CategoryResponse.class), anyLong());
        verify(this.jwtTokenProvider, times(1)).getCurrentTokenAsHeader();
    }

}