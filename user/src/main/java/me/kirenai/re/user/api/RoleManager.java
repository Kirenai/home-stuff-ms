package me.kirenai.re.user.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.user.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoleManager {

    public static final String POST_ROLE_CREATE_URL = "http://ROLE/api/v0/roles/user/{userId}";

    private final WebClient.Builder webClient;

    public Mono<Void> createRoleUser(User user) {
        log.info("Invoking RoleManager.createRoleUser method");
        log.info("Call role service");
        return this.webClient
                .build()
                .post()
                .uri(POST_ROLE_CREATE_URL, user.getUserId())
                .retrieve()
                .bodyToMono(Void.class);
    }

}