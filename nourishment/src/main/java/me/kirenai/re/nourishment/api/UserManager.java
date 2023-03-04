package me.kirenai.re.nourishment.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.nourishment.dto.UserResponse;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserManager {

    private static final String USER_URL_GET_ONE = "http://USER/api/v0/users/{userId}";

    private final RestTemplate restTemplate;
    private final JwtTokenProvider jwtTokenProvider;

    public UserResponse findUser(Long userId) {
        log.info("Invoking UserManager.findUser method");
        log.info("Call user service");
        return this.restTemplate.exchange(
                USER_URL_GET_ONE,
                HttpMethod.GET,
                new HttpEntity<>(this.jwtTokenProvider.getCurrentTokenAsHeader()),
                UserResponse.class,
                userId
        ).getBody();
    }

}
