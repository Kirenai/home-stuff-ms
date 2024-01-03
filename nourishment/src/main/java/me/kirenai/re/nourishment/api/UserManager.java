package me.kirenai.re.nourishment.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.nourishment.dto.UserResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserManager {

    private static final String USER_URL_GET_ONE = "http://USER/api/v0/users/{userId}";

    private final WebClient webClient;

    public Mono<UserResponse> findUser(Long userId) {
        log.info("Invoking UserManager.findUser method");
        log.info("Call user service");
        return this.webClient
                .get()
                .uri(USER_URL_GET_ONE, userId)
                .retrieve()
                .bodyToMono(UserResponse.class);
    }

}