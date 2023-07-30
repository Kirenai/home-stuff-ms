package me.kirenai.re.exception.entity;

import org.springframework.web.reactive.function.server.ServerRequest;

public abstract class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public abstract EntityErrorMessage createError(ServerRequest request, EntityNotFoundException exception);

}
