package me.kirenai.re.user.router;

import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.user.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Slf4j
@Configuration
public class UserRouter {

    private static final String PATH = "/api/v0/users";

    @Bean
    public RouterFunction<ServerResponse> router(UserHandler userHandler) {
        return RouterFunctions.route()
                .GET(PATH, userHandler::getAll)
                .GET(PATH + "/{userId}", userHandler::getOne)
                .GET(PATH + "/details/{username}", userHandler::getOneByUsername)
                .POST(PATH, userHandler::save)
                .PUT(PATH + "/{userId}", userHandler::update)
                .DELETE(PATH + "/{userId}", userHandler::delete)
                .build();
    }

}
