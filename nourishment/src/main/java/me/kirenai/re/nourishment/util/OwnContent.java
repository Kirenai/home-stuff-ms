package me.kirenai.re.nourishment.util;

import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.nourishment.dto.NourishmentResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.function.BiFunction;

@Slf4j
@Configuration
public class OwnContent {

    @Bean
    public BiFunction<Mono<NourishmentResponse>, Map<String, Object>, Mono<Boolean>> isAuthorized() {
        return (nourishment, principal) -> {
            log.info("Invoking @isAuthorized.apply() method");
            boolean adminMatch = AdminUtils.adminMatch(principal);
            if (adminMatch) {
                return Mono.defer(() -> Mono.just(Boolean.TRUE));
            }
            return Mono.defer(() -> nourishment.map(n -> n.getUserId().equals(Long.valueOf(principal.get("userId").toString()))));
        };
    }

    @Bean
    public BiFunction<Long, Map<String, Object>, Mono<Boolean>> isOwnToken() {
        return (userId, principal) -> {
            log.info("Invoking @isOwnToken.apply() method");
            boolean adminMatch = AdminUtils.adminMatch(principal);
            if (adminMatch) {
                return Mono.defer(() -> Mono.just(Boolean.TRUE));
            }
            Long userIdToken = Long.valueOf(principal.get("userId").toString());
            return Mono.defer(() -> Mono.just(userIdToken.equals(userId) ? Boolean.TRUE : Boolean.FALSE));
        };
    }

}
