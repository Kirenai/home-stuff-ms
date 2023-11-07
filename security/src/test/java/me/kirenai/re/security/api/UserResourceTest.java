package me.kirenai.re.security.api;

import me.kirenai.re.security.dto.UserResponse;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import me.kirenai.re.security.util.UserResponseMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserResourceTest {

    @InjectMocks
    private UserResource userResource;
    @Mock
    private WebClient.Builder webClient;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void getUserTest() throws IOException {
        UserResponse userResponse = UserResponseMock.getInstance().getUserResponse();

        WebClient webClientMock = mock(WebClient.class);
        WebClient.RequestHeadersUriSpec requestHeaderUriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.ResponseSpec responseSpecMock = mock(WebClient.ResponseSpec.class);

        when(this.webClient.build()).thenReturn(webClientMock);
        when(webClientMock.get()).thenReturn(requestHeaderUriSpecMock);
        when(requestHeaderUriSpecMock.uri(anyString(), anyString()))
                .thenReturn(requestHeaderUriSpecMock);
        when(this.jwtTokenProvider.getAuthorizationHeader()).thenReturn(HttpHeaders.AUTHORIZATION);
        when(this.jwtTokenProvider.getTokenPrefix()).thenReturn("Prefix");
        when(requestHeaderUriSpecMock.header(anyString(), anyString()))
                .thenReturn(requestHeaderUriSpecMock);
        when(requestHeaderUriSpecMock.retrieve())
                .thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(UserResponse.class))
                .thenReturn(Mono.just(userResponse));

        Mono<UserResponse> response = this.userResource.getUser("username", "token");

        StepVerifier.create(response)
                .expectNext(userResponse)
                .verifyComplete();
    }

}
