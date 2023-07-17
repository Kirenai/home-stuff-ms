package me.kirenai.re.auth.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.auth.dto.RegisterRequest;
import me.kirenai.re.auth.dto.UserResponse;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserManager {

    public static final String POST_USER_URL = "http://USER/api/v0/users";

    private final WebClient.Builder webClient;
    private final JwtTokenProvider jwtTokenProvider;

    public Mono<UserResponse> createUser(RegisterRequest request) {
        String token = this.jwtTokenProvider.generateInternalJwtToken();
        return this.webClient.build()
                .post()
                .uri(POST_USER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), RegisterRequest.class)
                .header(this.jwtTokenProvider.getAuthorizationHeader(), this.jwtTokenProvider.getTokenPrefix() + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(UserResponse.class);
    }

}
