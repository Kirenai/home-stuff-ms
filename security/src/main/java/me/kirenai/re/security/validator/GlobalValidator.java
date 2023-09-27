package me.kirenai.re.security.validator;

import lombok.AllArgsConstructor;
import me.kirenai.re.security.dto.ErrorResponse;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
public class GlobalValidator {

    private final Validator validator;

    public List<ErrorResponse> validate(Object object) {
        BindingResult bindingResult = new BeanPropertyBindingResult(object, object.getClass().getName());
        validator.validate(object, bindingResult);
        if (bindingResult.hasErrors()) {
            return bindingResult.getFieldErrors()
                    .stream()
                    .map(ErrorResponse::new)
                    .toList();
        }
        return Collections.emptyList();
    }

}
