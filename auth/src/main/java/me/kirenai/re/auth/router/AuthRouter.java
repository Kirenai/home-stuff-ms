package me.kirenai.re.auth.router;

import me.kirenai.re.auth.handler.AuthHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class AuthRouter {

    private static final String PATH = "/api/v0/auth";

    @Bean
    public RouterFunction<ServerResponse> route(AuthHandler handler) {
        return RouterFunctions.route()
                .POST(PATH + "/login", handler::login)
                .build();
    }
}
