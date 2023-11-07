package me.kirenai.re.security.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.security.dto.RoleResponse;
import me.kirenai.re.security.dto.UserResponse;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static me.kirenai.re.security.util.Constants.GET_ROLES_USER_URL;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoleUserResource {

    private final WebClient.Builder webClient;
    private final JwtTokenProvider jwtTokenProvider;

    public Mono<List<SimpleGrantedAuthority>> getAuthorities(Mono<UserResponse> userResponseMono, String token) {
        log.info("Invoke RoleUserResource.getAuthorities method");
        WebClient client = this.webClient.build();
        return userResponseMono
                .flatMap(user -> client
                        .get()
                        .uri(GET_ROLES_USER_URL, user.getUserId())
                        .header(this.jwtTokenProvider.getAuthorizationHeader(), this.jwtTokenProvider.getTokenPrefix() + token)
                        .retrieve()
                        .bodyToFlux(RoleResponse.class)
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collectList()
                );
    }

}
