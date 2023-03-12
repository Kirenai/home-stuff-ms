package me.kirenai.re.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("users", r -> r.path("/api/v0/users/**")
                        .uri("lb://USER"))
                .route("roles", r -> r.path("/api/v0/roles/**")
                        .uri("lb://ROLE"))
                .route("nourishments", r -> r.path("/api/v0/nourishments/**")
                        .uri("lb://NOURISHMENT"))
                .route("consumptions", r -> r.path("/api/v0/consumptions/**")
                        .uri("lb://CONSUMPTION"))
                .route("categories", r -> r.path("/api/v0/categories/**")
                        .uri("lb://CATEGORY"))
                .route("auth", r -> r.path("/api/v0/auth/**")
                        .uri("lb://AUTH"))
                .build();
    }

}