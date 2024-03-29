package me.kirenai.re.auth.api;

import me.kirenai.re.auth.dto.RegisterRequest;
import me.kirenai.re.auth.dto.UserResponse;
import me.kirenai.re.auth.util.AuthMocks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserManagerTest {

    @InjectMocks
    private UserManager userManager;
    @Mock
    private WebClient webClient;

    @Test
    public void createUserTest() throws IOException {
        WebClient.RequestBodyUriSpec requestBodyUriSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.RequestBodySpec requestBodySpec = mock(WebClient.RequestBodySpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

        when(this.webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.contentType(any())).thenReturn(requestBodySpec);
        when(requestBodySpec.body(any(), eq(RegisterRequest.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.accept(any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(eq(UserResponse.class))).thenReturn(Mono.just(AuthMocks.getUserResponse()));

        this.userManager.postCreateUser(new RegisterRequest());

        verify(this.webClient, times(1)).post();
        verify(requestBodyUriSpec, times(1)).uri(anyString());
        verify(requestBodyUriSpec, times(1)).contentType(any());
        verify(requestBodySpec, times(1)).body(any(), eq(RegisterRequest.class));
        verify(requestHeadersSpec, times(1)).accept(any());
        verify(requestHeadersSpec, times(1)).retrieve();
        verify(responseSpec, times(1)).bodyToMono(eq(UserResponse.class));
    }


}