package me.kirenai.re.auth.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.security.dto.RoleResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static me.kirenai.re.auth.util.Constants.GET_ROLES_USER_ID_URL;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoleUserManager {

    private final WebClient webClient;

    public Mono<List<String>> getRolesByUserId(Long userId) {
        log.info("Invoke RoleUserResource.getAuthorities method");
        return this.webClient
                .get()
                .uri(GET_ROLES_USER_ID_URL, userId)
                .retrieve()
                .bodyToFlux(RoleResponse.class)
                .map(RoleResponse::getName)
                .collectList();
//        return userResponseMono
//                .flatMap(user -> client
//                        .get()
//                        .uri(GET_ROLES_USER_ID_URL, user.getUserId())
//                        .retrieve()
//                        .bodyToFlux(String.class)
//                        .collectList()
//                );
    }

}
