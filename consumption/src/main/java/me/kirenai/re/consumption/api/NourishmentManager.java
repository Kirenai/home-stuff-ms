package me.kirenai.re.consumption.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.consumption.dto.NourishmentRequest;
import me.kirenai.re.consumption.dto.NourishmentResponse;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class NourishmentManager {

    private static final String NOURISHMENT_GET_ONE_URL = "http://NOURISHMENT/api/v0/nourishments/{nourishmentsId}";
    private static final String NOURISHMENT_PUT_URL = "http://NOURISHMENT/api/v0/nourishments/{nourishmentId}";

    private final RestTemplate restTemplate;
    private final JwtTokenProvider jwtTokenProvider;

    public NourishmentResponse findNourishment(Long nourishmentId) {
        log.info("Invoking NourishmentManager.findNourishment method");
        log.info("Call nourishment service");
        return this.restTemplate.exchange(
                NOURISHMENT_GET_ONE_URL,
                HttpMethod.GET,
                new HttpEntity<>(this.jwtTokenProvider.getCurrentTokenAsHeader()),
                NourishmentResponse.class,
                nourishmentId
        ).getBody();
    }

    public void updateNourishment(NourishmentRequest nourishmentRequest, NourishmentResponse nourishmentResponse) {
        log.info("Invoking NourishmentManager.updateNourishment method");
        log.info("Call nourishment service");
        this.restTemplate.exchange(
                NOURISHMENT_PUT_URL,
                HttpMethod.PUT,
                new HttpEntity<>(nourishmentRequest, this.jwtTokenProvider.getCurrentTokenAsHeader()),
                Void.class,
                nourishmentResponse.getNourishmentId()
        );
    }

}
