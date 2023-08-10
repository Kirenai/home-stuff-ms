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

import java.util.HashMap;
import java.util.Map;

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
            Map<String, Object> principal = new HashMap<>();
            principal.put("userId", claims.getId());
            principal.put("username", claims.getSubject());
            principal.put("authorities", this.jwtTokenProvider.getJwtGrantedAuthorities(token));
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    principal,
                    null,
                    this.jwtTokenProvider.getJwtGrantedAuthorities(token)
            );
            return chain.filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
        }
        return chain.filter(exchange);
    }

}