package me.kirenai.re.consumption.api;

import me.kirenai.re.consumption.dto.NourishmentRequest;
import me.kirenai.re.consumption.dto.NourishmentResponse;
import me.kirenai.re.consumption.util.NourishmentMocks;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NourishmentManagerTest {

    @InjectMocks
    private NourishmentManager nourishmentManager;
    @Mock
    private WebClient.Builder webClient;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void findNourishmentTest() {
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
        when(responseSpecMock.bodyToMono(NourishmentResponse.class))
                .thenReturn(Mono.just(NourishmentMocks.getNourishmentResponse()));

        Mono<NourishmentResponse> userResponse = this.nourishmentManager.findNourishment(1L, "Bearer token");

        StepVerifier
                .create(userResponse)
                .expectNext(NourishmentMocks.getNourishmentResponse())
                .verifyComplete();
    }

    @Test
    public void updateNourishmentTest() {
        WebClient webClientMock = mock(WebClient.class);
        WebClient.RequestBodyUriSpec requestBodyUriSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.ResponseSpec responseSpecMock = mock(WebClient.ResponseSpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);

        when(this.webClient.build()).thenReturn(webClientMock);
        when(webClientMock.put()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString(), anyLong()))
                .thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.contentType(any())).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.body(any(), eq(NourishmentRequest.class))).thenReturn(requestHeadersSpec);
        when(this.jwtTokenProvider.getAuthorizationHeader()).thenReturn(HttpHeaders.AUTHORIZATION);
        when(requestHeadersSpec.header(anyString(), anyString()))
                .thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.retrieve())
                .thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(Void.class))
                .thenReturn(Mono.empty());

        Mono<Void> response = this.nourishmentManager.updateNourishment(
                new NourishmentRequest(), NourishmentMocks.getNourishmentResponse(), "Bearer token"
        );

        StepVerifier
                .create(response)
                .verifyComplete();
    }

}