package me.kirenai.re.user.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import me.kirenai.re.user.entity.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoleManager {

    public static final String POST_ROLE_CREATE_URL = "http://ROLE/api/v0/roles/user/{userId}";

    private final RestTemplate restTemplate;
    private final JwtTokenProvider jwtTokenProvider;

    public void createRoleUser(User user) {
        log.info("Invoking RoleManager.createRoleUser method");
        log.info("Call role service");
        this.restTemplate.exchange(
                POST_ROLE_CREATE_URL,
                HttpMethod.POST,
                new HttpEntity<>(this.jwtTokenProvider.getCurrentTokenAsHeader()),
                Void.class,
                user.getUserId()
        );
    }

}