package me.kirenai.re.exception.handler;

import me.kirenai.re.exception.entity.EntityNotFoundException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        HashMap<String, Object> errorAttributes = new HashMap<>();
        Throwable throwable = super.getError(request);

        if (throwable instanceof EntityNotFoundException exception) {
            errorAttributes.put("error", exception.createError(request, exception));
            errorAttributes.put("status", HttpStatus.NOT_FOUND);
        }

        return errorAttributes;
    }

}
