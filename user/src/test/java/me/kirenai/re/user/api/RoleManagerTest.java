package me.kirenai.re.user.api;

import me.kirenai.re.security.jwt.JwtTokenProvider;
import me.kirenai.re.user.util.UserMocks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleManagerTest {

    @InjectMocks
    private RoleManager roleManager;
    @Mock
    private WebClient.Builder webClient;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("Should call role service")
    public void createRoleUserTest() {
        WebClient webClientMock = mock(WebClient.class);
        WebClient.RequestBodyUriSpec requestBodyUriSpecMock = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec requestBodySpecMock = mock(WebClient.RequestBodySpec.class);
        WebClient.ResponseSpec responseSpecMock = mock(WebClient.ResponseSpec.class);

        when(this.webClient.build()).thenReturn(webClientMock);
        when(webClientMock.post()).thenReturn(requestBodyUriSpecMock);
        when(requestBodyUriSpecMock.uri(anyString(), anyLong())).thenReturn(requestBodySpecMock);
        when(this.jwtTokenProvider.getAuthorizationHeader()).thenReturn("header");
        when(requestBodySpecMock.header(anyString(), anyString())).thenReturn(requestBodySpecMock);
        when(requestBodySpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(eq(Void.class))).thenReturn(Mono.empty());

        this.roleManager.createRoleUser(UserMocks.getUser(), "token");

        verify(this.jwtTokenProvider, times(1)).getAuthorizationHeader();
    }

}