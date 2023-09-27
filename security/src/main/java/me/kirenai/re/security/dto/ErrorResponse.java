package me.kirenai.re.security.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.validation.FieldError;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        String field,
        String defaultMessage
) {

    public ErrorResponse(FieldError fieldError) {
        this(fieldError.getField(), fieldError.getDefaultMessage());
    }

}
