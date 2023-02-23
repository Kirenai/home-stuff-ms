package me.kirenai.re.nourishment.util;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class NourishmentClient {

    private final RestTemplate restTemplate;

    public <R> R getResponse(Long param, String url, Class<R> clazz) {
        return this.restTemplate.getForObject(url, clazz, param);
    }

    public <R> ResponseEntity<R> exchange(
            String url,
            HttpMethod method,
            HttpEntity<?> requestEntity,
            Class<R> responseType,
            Object... uriVariables) {
        return this.restTemplate.exchange(url, method, requestEntity, responseType, uriVariables);
    }

}
