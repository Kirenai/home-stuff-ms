package me.kirenai.re.security.config;

import lombok.RequiredArgsConstructor;
import me.kirenai.re.security.jwt.JwtTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityFilterChainConfig {

    private final JwtTokenFilter jwtTokenFilter;
    private final ReactiveAuthenticationManager reactiveAuthenticationManager;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf().disable()
                .authenticationManager(this.reactiveAuthenticationManager)
                .authorizeExchange(authorize -> authorize
                        .pathMatchers(HttpMethod.POST, "/api/v0/auth/**").permitAll()
                        .anyExchange().authenticated())
                .addFilterBefore(this.jwtTokenFilter, SecurityWebFiltersOrder.AUTHORIZATION);
        return http.build();
    }

}