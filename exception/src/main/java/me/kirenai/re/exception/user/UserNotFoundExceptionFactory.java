package me.kirenai.re.exception.user;

import me.kirenai.re.exception.entity.EntityErrorMessage;
import me.kirenai.re.exception.entity.EntityNotFoundExceptionFactory;
import me.kirenai.re.exception.util.ServiceEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.time.LocalDateTime;

public class UserNotFoundExceptionFactory extends EntityNotFoundExceptionFactory {

    public UserNotFoundExceptionFactory(String message) {
        super(message);
    }

    @Override
    public EntityErrorMessage createError(ServerRequest request, EntityNotFoundExceptionFactory exception) {
        return UserErrorMessage.builder()
                .message(exception.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .path(request.path())
                .service(ServiceEnum.USER)
                .build();
    }

}
