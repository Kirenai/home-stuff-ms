package me.kirenai.re.security.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.security.dto.RoleResponse;
import me.kirenai.re.security.dto.UserResponse;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import me.kirenai.re.security.service.AuthUserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static me.kirenai.re.security.util.Constants.GET_ONE_USER_DETAILS_URL;
import static me.kirenai.re.security.util.Constants.GET_ROLES_USER_URL;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserResource {

    private final WebClient.Builder webClient;
    private final JwtTokenProvider jwtTokenProvider;

    public Mono<UserDetails> getUserDetails(String username) {
        log.info("Invoke SecurityUtil.getUserDetails method");
        String token = this.jwtTokenProvider.generateInternalJwtToken();
        WebClient client = this.webClient.build();
        Mono<UserResponse> userResponseMono = client
                .get()
                .uri(GET_ONE_USER_DETAILS_URL, username)
                .header(this.jwtTokenProvider.getAuthorizationHeader(), this.jwtTokenProvider.getTokenPrefix() + token)
                .retrieve()
                .bodyToMono(UserResponse.class);
        Mono<List<SimpleGrantedAuthority>> roleResponseMono = userResponseMono
                .flatMap(user -> client
                        .get()
                        .uri(GET_ROLES_USER_URL, user.getUserId())
                        .header(this.jwtTokenProvider.getAuthorizationHeader(), this.jwtTokenProvider.getTokenPrefix() + token)
                        .retrieve()
                        .bodyToFlux(RoleResponse.class)
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collectList()
                );
        return userResponseMono
                .zipWith(roleResponseMono)
                .map(tuple -> new AuthUserDetails(
                                tuple.getT1().getUserId(),
                                tuple.getT1().getUsername(),
                                tuple.getT1().getPassword(),
                                tuple.getT2()
                        )
                );
    }

}