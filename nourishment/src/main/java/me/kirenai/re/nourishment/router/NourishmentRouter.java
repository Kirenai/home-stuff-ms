package me.kirenai.re.nourishment.router;

import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.nourishment.handler.NourishmentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Slf4j
@Configuration
public class NourishmentRouter {

    public final String URL = "/api/v0/nourishments";

    @Bean
    public RouterFunction<ServerResponse> route(NourishmentHandler nourishmentHandler) {
        return RouterFunctions.route()
                .GET(URL, nourishmentHandler::getAll)
                .GET(URL + "/{nourishmentId}", nourishmentHandler::getOne)
                .GET(URL + "/user/{userId}", nourishmentHandler::getAllByUserId)
                .GET(URL + "/isAvailable/{isAvailable}", nourishmentHandler::getAllByIsAvailable)
                .POST(URL + "/user/{userId}/category/{categoryId}", nourishmentHandler::save)
                .PUT(URL + "/{nourishmentId}", nourishmentHandler::update)
                .DELETE(URL + "/{nourishmentId}", nourishmentHandler::delete)
                .build();
    }

}