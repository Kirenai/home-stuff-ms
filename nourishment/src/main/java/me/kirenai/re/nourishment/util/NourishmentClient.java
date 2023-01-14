package me.kirenai.re.nourishment.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class NourishmentClient {

    private final RestTemplate restTemplate;

    public <R> R getResponse(Long param, String url, Class<R> clazz ) {
        return this.restTemplate.getForObject(url, clazz, param);
    }


}
