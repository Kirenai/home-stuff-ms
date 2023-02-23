package me.kirenai.re.consumption.util;

import me.kirenai.re.consumption.dto.NourishmentRequest;
import me.kirenai.re.consumption.dto.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ConsumptionClientTest {

    @InjectMocks
    private ConsumptionClient client;
    @Mock
    private RestTemplate restTemplate;

    @Test
    public void shouldExchangeTest() {
        this.client.exchange("url", HttpMethod.GET, new HttpEntity<>(new Object()), UserResponse.class, 1L);
        verify(this.restTemplate, timeout(1)).exchange(anyString(), any(), any(), eq(UserResponse.class), anyLong());
    }

    @Test
    public void shouldPutRequestTest() {
        this.client.putRequest("url", NourishmentRequest.builder().build(), 1L);
        verify(this.restTemplate, timeout(1)).put(anyString(), any(), any(Object.class));
    }

}