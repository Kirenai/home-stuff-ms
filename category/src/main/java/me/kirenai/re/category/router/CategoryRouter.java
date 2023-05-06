package me.kirenai.re.category.router;

import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.category.handler.CategoryHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Slf4j
@Configuration
public class CategoryRouter {

    private static final String PATH = "/api/v0/categories";

    @Bean
    public RouterFunction<ServerResponse> router(CategoryHandler handler) {
        return RouterFunctions.route()
                .GET(PATH + "/{categoryId}", handler::findOne)
                .POST(PATH, handler::create)
                .PUT(PATH + "/{categoryId}", handler::update)
                .DELETE(PATH + "/{categoryId}", handler::delete)
                .build();
    }

}
