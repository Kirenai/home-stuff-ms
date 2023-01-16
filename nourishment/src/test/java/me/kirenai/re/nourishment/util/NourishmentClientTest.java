package me.kirenai.re.nourishment.util;

import me.kirenai.re.nourishment.dto.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NourishmentClientTest {

    @InjectMocks
    private NourishmentClient nourishmentClient;

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void getResponseTest() {
        Mockito.when(this.restTemplate.getForObject(anyString(), eq(UserResponse.class), anyLong()))
                .thenReturn(new UserResponse());

        UserResponse response = this.nourishmentClient.getResponse(1L, "URL", UserResponse.class);

        assertNotNull(response);
        verify(this.restTemplate, timeout(1)).getForObject(anyString(), any(), anyLong());
    }


}