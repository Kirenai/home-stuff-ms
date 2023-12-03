package me.kirenai.re.auth.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.auth.dto.RegisterRequest;
import me.kirenai.re.auth.dto.UserResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static me.kirenai.re.auth.util.Constants.GET_USER_USERNAME_URL;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserManager {

    public static final String POST_USER_URL = "http://USER/api/v0/users";

    private final WebClient.Builder webClient;

    public Mono<UserResponse> postCreateUser(RegisterRequest request) {
        log.info("Invoke UserResource.postCreateUser method");
        return this.webClient.build()
                .post()
                .uri(POST_USER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), RegisterRequest.class)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(UserResponse.class);
    }

    public Mono<UserResponse> getUserByUsername(String username) {
        log.info("Invoke UserResource.getUserByUsername method");
        WebClient client = this.webClient.build();
        return client
                .get()
                .uri(GET_USER_USERNAME_URL, username)
                .retrieve()
                .bodyToMono(UserResponse.class);
    }

}
