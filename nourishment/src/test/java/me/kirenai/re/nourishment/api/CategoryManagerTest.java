package me.kirenai.re.nourishment.api;

import me.kirenai.re.nourishment.dto.CategoryResponse;
import me.kirenai.re.nourishment.util.CategoryMocks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryManagerTest {

    @InjectMocks
    private CategoryManager categoryManager;
    @Mock
    private WebClient.Builder webClient;

    @Test
    @DisplayName("Should verify the category service call")
    public void findCategoryTest() throws IOException {
        WebClient webClientMock = mock(WebClient.class);
        WebClient.RequestHeadersUriSpec requestHeaderUriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.ResponseSpec responseSpecMock = mock(WebClient.ResponseSpec.class);

        when(this.webClient.build()).thenReturn(webClientMock);
        when(webClientMock.get()).thenReturn(requestHeaderUriSpecMock);
        when(requestHeaderUriSpecMock.uri(anyString(), anyLong()))
                .thenReturn(requestHeaderUriSpecMock);
        when(requestHeaderUriSpecMock.retrieve())
                .thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(CategoryResponse.class))
                .thenReturn(Mono.just(CategoryMocks.getInstance().getCategoryResponse()));

        this.categoryManager.findCategory(1L);
    }

}