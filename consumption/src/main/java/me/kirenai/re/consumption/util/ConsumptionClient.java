package me.kirenai.re.consumption.util;

import lombok.RequiredArgsConstructor;
import me.kirenai.re.consumption.dto.NourishmentRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ConsumptionClient {

    private final RestTemplate restTemplate;

    public <R> R getResponse(Long param, String url, Class<R> clazz) {
        return this.restTemplate.getForObject(url, clazz, param);
    }

    public void putRequest(String url, NourishmentRequest request, Object... uriVariables) {
        this.restTemplate.put(url, request, uriVariables);
    }

}