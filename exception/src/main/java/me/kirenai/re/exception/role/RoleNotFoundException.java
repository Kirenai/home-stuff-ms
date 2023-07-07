package me.kirenai.re.exception.role;

import me.kirenai.re.exception.entity.EntityErrorMessage;
import me.kirenai.re.exception.entity.EntityNotFoundException;
import me.kirenai.re.exception.util.ServiceEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.time.LocalDateTime;

public class RoleNotFoundException extends EntityNotFoundException {

    public RoleNotFoundException(String message) {
        super(message);
    }

    @Override
    public EntityErrorMessage error(ServerRequest request, EntityNotFoundException exception) {
        return RoleErrorMessage.builder()
                .message(exception.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .path(request.path())
                .service(ServiceEnum.ROLE)
                .build();
    }

}
