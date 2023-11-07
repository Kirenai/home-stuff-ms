package me.kirenai.re.security.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.security.dto.UserResponse;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static me.kirenai.re.security.util.Constants.GET_ONE_USER_DETAILS_URL;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserResource {

    private final WebClient.Builder webClient;
    private final JwtTokenProvider jwtTokenProvider;

    public Mono<UserResponse> getUser(String username, String token) {
        log.info("Invoke UserResource.getUser method");
        WebClient client = this.webClient.build();
        return client
                .get()
                .uri(GET_ONE_USER_DETAILS_URL, username)
                .header(this.jwtTokenProvider.getAuthorizationHeader(), this.jwtTokenProvider.getTokenPrefix() + token)
                .retrieve()
                .bodyToMono(UserResponse.class);
    }

}