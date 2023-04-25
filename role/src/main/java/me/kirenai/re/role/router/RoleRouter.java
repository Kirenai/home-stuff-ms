package me.kirenai.re.role.router;

import me.kirenai.re.role.handler.RoleHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RoleRouter {

    private static final String URL = "/api/v0/roles";

    @Bean
    public RouterFunction<ServerResponse> router(RoleHandler roleHandler) {
        return RouterFunctions.route()
                .GET(URL + "/{roleId}", roleHandler::getRole)
                .GET(URL + "/user/{userId}", roleHandler::getRolesByUserId)
                .POST(URL, roleHandler::save)
                .POST(URL + "/user/{userId}", roleHandler::saveRoleUser)
                .PUT(URL + "/{roleId}", roleHandler::update)
                .build();
    }

}