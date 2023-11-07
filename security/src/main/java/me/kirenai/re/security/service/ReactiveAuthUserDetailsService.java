package me.kirenai.re.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.security.api.UserDetailsResource;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReactiveAuthUserDetailsService implements ReactiveUserDetailsService {

    private final UserDetailsResource userDetailsResource;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        log.info("Invoke ReactiveAuthUserDetailsService.findByUsername method");
        return this.userDetailsResource.getUserDetails(username);
    }

}