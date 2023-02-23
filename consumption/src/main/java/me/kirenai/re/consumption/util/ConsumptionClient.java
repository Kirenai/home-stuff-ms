package me.kirenai.re.consumption.util;

import lombok.RequiredArgsConstructor;
import me.kirenai.re.consumption.dto.NourishmentRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ConsumptionClient {

    private final RestTemplate restTemplate;

    public <R> ResponseEntity<R> exchange(
            String url,
            HttpMethod method,
            HttpEntity<?> requestEntity,
            Class<R> responseType,
            Object... uriVariable) {
        return this.restTemplate.exchange(url, method, requestEntity, responseType, uriVariable);
    }

    public void putRequest(String url, NourishmentRequest request, Object... uriVariables) {
        this.restTemplate.put(url, request, uriVariables);
    }

}