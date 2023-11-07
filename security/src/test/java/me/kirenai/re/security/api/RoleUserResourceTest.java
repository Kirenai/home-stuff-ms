package me.kirenai.re.security.api;

import me.kirenai.re.security.dto.UserResponse;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import me.kirenai.re.security.util.UserResponseMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleUserResourceTest {

    @InjectMocks
    private RoleUserResource roleUserResource;
    @Mock
    private WebClient.Builder webClient;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void getAuthoritiesTest() throws IOException {
        UserResponse userResponse = UserResponseMock.getInstance().getUserResponse();

        WebClient webClientMock = mock(WebClient.class);

        when(this.webClient.build()).thenReturn(webClientMock);

        this.roleUserResource.getAuthorities(Mono.just(userResponse), "token");

        verify(this.webClient, times(1)).build();
    }

}