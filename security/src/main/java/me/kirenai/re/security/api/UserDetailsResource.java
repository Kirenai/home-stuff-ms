package me.kirenai.re.security.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.security.dto.UserResponse;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import me.kirenai.re.security.service.AuthUserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDetailsResource {

    private final JwtTokenProvider jwtTokenProvider;
    private final RoleUserResource roleUserResource;
    private final UserResource userResource;

    public Mono<UserDetails> getUserDetails(String username) {
        log.info("Invoke UserDetailsResource.getUserDetails method");
        String token = this.jwtTokenProvider.generateInternalJwtToken();
        Mono<UserResponse> userResponseMono = this.userResource.getUser(username, token);
        Mono<List<SimpleGrantedAuthority>> authorities = this.roleUserResource.getAuthorities(userResponseMono, token);
        return userResponseMono
                .zipWith(authorities)
                .map(tuple -> new AuthUserDetails(
                                tuple.getT1().getUserId(),
                                tuple.getT1().getUsername(),
                                tuple.getT1().getPassword(),
                                tuple.getT2()
                        )
                );
    }
}
