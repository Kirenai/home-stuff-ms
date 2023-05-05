package me.kirenai.re.consumption.router;

import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.consumption.handler.ConsumptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Slf4j
@Configuration
public class ConsumptionRouter {

    private static final String PATH = "/api/v0/consumptions";

    @Bean
    public RouterFunction<ServerResponse> router(ConsumptionHandler consumptionHandler) {
        return RouterFunctions.route()
                .GET(PATH + "/{consumptionId}", consumptionHandler::findOne)
                .POST(PATH + "/user/{userId}/nourishment/{nourishmentId}", consumptionHandler::save)
                .build();
    }
}
