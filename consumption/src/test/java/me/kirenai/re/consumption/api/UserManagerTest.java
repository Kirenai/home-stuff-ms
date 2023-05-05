package me.kirenai.re.consumption.api;

import me.kirenai.re.consumption.dto.UserResponse;
import me.kirenai.re.consumption.util.UserMocks;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserManagerTest {

    @InjectMocks
    private UserManager userManager;
    @Mock
    private WebClient.Builder webClient;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("Should verify user service call")
    public void findUserTest() {
        WebClient webClientMock = mock(WebClient.class);
        WebClient.RequestHeadersUriSpec requestHeaderUriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.ResponseSpec responseSpecMock = mock(WebClient.ResponseSpec.class);

        when(this.webClient.build()).thenReturn(webClientMock);
        when(webClientMock.get()).thenReturn(requestHeaderUriSpecMock);
        when(requestHeaderUriSpecMock.uri(anyString(), anyLong()))
                .thenReturn(requestHeaderUriSpecMock);
        when(this.jwtTokenProvider.getAuthorizationHeader()).thenReturn(HttpHeaders.AUTHORIZATION);
        when(requestHeaderUriSpecMock.header(anyString(), anyString()))
                .thenReturn(requestHeaderUriSpecMock);
        when(requestHeaderUriSpecMock.retrieve())
                .thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(UserResponse.class))
                .thenReturn(Mono.just(UserMocks.getUserResponse()));

        Mono<UserResponse> userResponse = this.userManager.findUser(1L, "Bearer token");

        StepVerifier
                .create(userResponse)
                .expectNext(UserMocks.getUserResponse())
                .verifyComplete();
    }

}
