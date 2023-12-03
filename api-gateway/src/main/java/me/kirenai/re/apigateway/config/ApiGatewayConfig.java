package me.kirenai.re.apigateway.config;
import lombok.RequiredArgsConstructor;
import me.kirenai.re.apigateway.filter.AuthorizationGatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ApiGatewayConfig {

    private final AuthorizationGatewayFilter authorizationGatewayFilter;

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("users", r -> r.path("/users/**")
                        .filters(f -> f.rewritePath("/users(?<pathParam>/.*)?", "/api/v0/users${pathParam}")
                                .filter(this.authorizationGatewayFilter))
                        .uri("lb://USER"))
                .route("roles", r -> r.path("/roles/**")
                        .filters(f -> f.rewritePath("/roles(?<segment>.*)", "/api/v0/roles${segment}")
                                .filter(this.authorizationGatewayFilter))
                        .uri("lb://ROLE"))
                .route("nourishments", r -> r.path("/nourishments/**")
                        .filters(f -> f.rewritePath("/nourishments(?<segment>.*)", "/api/v0/nourishments${segment}")
                                .filter(this.authorizationGatewayFilter))
                        .uri("lb://NOURISHMENT"))
                .route("consumptions", r -> r.path("/consumptions/**")
                        .filters(f -> f.rewritePath("/consumptions(?<segment>.*)", "/api/v0/consumptions${segment}")
                                .filter(this.authorizationGatewayFilter))
                        .uri("lb://CONSUMPTION"))
                .route("categories", r -> r.path("/categories/**")
                        .filters(f -> f.rewritePath("/categories(?<segment>.*)", "/api/v0/categories${segment}")
                                .filter(this.authorizationGatewayFilter))
                        .uri("lb://CATEGORY"))
                .route("auth", r -> r.path("/auth/**")
                        .filters(f -> f.rewritePath("/auth(?<segment>.*)", "/api/v0/auth${segment}"))
                        .uri("lb://AUTH"))
                .build();
    }

}