package me.kirenai.re.user.api;

import me.kirenai.re.security.jwt.JwtTokenProvider;
import me.kirenai.re.user.util.UserMocks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RoleManagerTest {

    @InjectMocks
    private RoleManager roleManager;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("Should call role service")
    public void createRoleUserTest() {
        this.roleManager.createRoleUser(UserMocks.getUser());
        verify(this.restTemplate, times(1)).exchange(anyString(), any(), any(), eq(Void.class), anyLong());
        verify(this.jwtTokenProvider, times(1)).getCurrentTokenAsHeader();
    }

}