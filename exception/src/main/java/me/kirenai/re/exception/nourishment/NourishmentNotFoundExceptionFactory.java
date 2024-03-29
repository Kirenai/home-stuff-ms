package me.kirenai.re.exception.nourishment;

import me.kirenai.re.exception.entity.EntityErrorMessage;
import me.kirenai.re.exception.entity.EntityNotFoundExceptionFactory;
import me.kirenai.re.exception.util.ServiceEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.time.LocalDateTime;

public class NourishmentNotFoundExceptionFactory extends EntityNotFoundExceptionFactory {

    public NourishmentNotFoundExceptionFactory(String message) {
        super(message);
    }

    @Override
    public EntityErrorMessage createError(ServerRequest request, EntityNotFoundExceptionFactory exception) {
        return NourishmentErrorMessage.builder()
                .message(exception.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .path(request.path())
                .service(ServiceEnum.NOURISHMENT)
                .build();
    }

}
