package me.kirenai.re.exception.entity;

import org.springframework.web.reactive.function.server.ServerRequest;

public abstract class EntityNotFoundExceptionFactory extends RuntimeException {

    public EntityNotFoundExceptionFactory(String message) {
        super(message);
    }

    public abstract EntityErrorMessage createError(ServerRequest request, EntityNotFoundExceptionFactory exception);

}
