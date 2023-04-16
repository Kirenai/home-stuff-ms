package me.kirenai.re.security.jwt;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter implements WebFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        log.info("Invoking JwtTokenFilter.filter method");
        String token = this.jwtTokenProvider.getJwtTokenFromRequest(exchange.getRequest());
        if (this.jwtTokenProvider.validateJwtToken(token)) {
            Claims claims = this.jwtTokenProvider.getJwtBody(token);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    claims.getSubject(),
                    null,
                    this.jwtTokenProvider.getJwtGrantedAuthorities(token)
            );
            return chain.filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
        }
        return chain.filter(exchange);
    }

}