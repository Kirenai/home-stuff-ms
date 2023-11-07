package me.kirenai.re.security.api;

import me.kirenai.re.security.dto.UserResponse;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import me.kirenai.re.security.util.UserResponseMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsResourceTest {

    @InjectMocks
    private UserDetailsResource userDetailsResource;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private RoleUserResource roleUserResource;
    @Mock
    private UserResource userResource;

    @Test
    void getUserDetailsTest() throws IOException {
        UserResponse userResponse = UserResponseMock.getInstance().getUserResponse();

        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        when(this.jwtTokenProvider.generateInternalJwtToken()).thenReturn("token");
        when(this.userResource.getUser(anyString(), anyString())).thenReturn(Mono.just(userResponse));
        when(this.roleUserResource.getAuthorities(any(), anyString())).thenReturn(Mono.just(simpleGrantedAuthorities));

        this.userDetailsResource.getUserDetails("username");

        verify(this.jwtTokenProvider, times(1)).generateInternalJwtToken();
        verify(this.userResource, times(1)).getUser(anyString(), anyString());
        verify(this.roleUserResource, times(1)).getAuthorities(any(), anyString());
    }

}