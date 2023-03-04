package me.kirenai.re.nourishment.api;

import me.kirenai.re.nourishment.dto.UserResponse;
import me.kirenai.re.nourishment.util.UserMocks;
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
class UserManagerTest {

    @InjectMocks
    private UserManager userManager;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("Should verify user service call")
    public void findUserTest() {
        when(this.restTemplate.exchange(anyString(), any(), any(), eq(UserResponse.class), anyLong()))
                .thenReturn(UserMocks.getUserResponseEntity());
        this.userManager.findUser(1L);
        verify(this.restTemplate, times(1)).exchange(anyString(), any(), any(), eq(UserResponse.class), anyLong());
        verify(this.jwtTokenProvider, timeout(1)).getCurrentTokenAsHeader();
    }

}