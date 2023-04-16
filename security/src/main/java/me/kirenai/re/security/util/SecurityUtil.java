package me.kirenai.re.security.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.security.dto.RoleResponse;
import me.kirenai.re.security.dto.UserResponse;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import me.kirenai.re.security.service.AuthUserDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityUtil {

    public static final String GET_ONE_USER_DETAILS_URL = "http://USER/api/v0/users/details/{username}";
    public static final String GET_ROLES_USER_URL = "http://ROLE/api/v0/roles/user/{userId}";
    private final WebClient.Builder webClient;
    private final JwtTokenProvider jwtTokenProvider;

    public Mono<UserDetails> getUserDetails(String username) {
        log.info("Invoke SecurityUtil.getUserDetails method");
        String token = this.jwtTokenProvider.generateInternalJwtToken();
        Mono<UserResponse> userResponseMono = this.webClient
                .build()
                .get()
                .uri(GET_ONE_USER_DETAILS_URL, username)
                .header(HttpHeaders.AUTHORIZATION, this.jwtTokenProvider.getTokenPrefix() + token)
                .retrieve()
                .bodyToMono(UserResponse.class);
//        Flux<SimpleGrantedAuthority> roleResponseFlux = userResponseMono
//                .flatMapMany(user -> this.webClient
//                        .get()
//                        .uri(GET_ROLES_USER_URL, user.getUserId())
//                        .header(HttpHeaders.AUTHORIZATION, this.jwtTokenProvider.getTokenPrefix() + token)
//                        .retrieve()
//                        .bodyToFlux(RoleResponse.class)
//                        .flatMap(Flux::just)
//                        .map(role -> new SimpleGrantedAuthority(role.getName()))
//                );
        return userResponseMono
                .map(user -> new AuthUserDetails(
                                user.getUserId(),
                                user.getUsername(),
                                user.getPassword(),
                                List.of(new SimpleGrantedAuthority("ROLE_USER"))
                        )
                );
    }

}