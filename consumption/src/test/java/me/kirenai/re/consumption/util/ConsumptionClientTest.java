package me.kirenai.re.consumption.util;

import me.kirenai.re.consumption.dto.NourishmentRequest;
import me.kirenai.re.consumption.dto.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ConsumptionClientTest {

    @InjectMocks
    private ConsumptionClient client;
    @Mock
    private RestTemplate restTemplate;

    @Test
    public void shouldGetResponseTest() {
        this.client.getResponse(1L, "url", UserResponse.class);
        verify(this.restTemplate, timeout(1)).getForObject(anyString(), any(), any(Object.class));
    }

    @Test
    public void shouldPutRequestTest() {
        this.client.putRequest("url", NourishmentRequest.builder().build(), 1L);
        verify(this.restTemplate, timeout(1)).put(anyString(), any(), any(Object.class));
    }

}