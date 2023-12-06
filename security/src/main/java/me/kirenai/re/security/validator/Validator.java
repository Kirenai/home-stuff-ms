package me.kirenai.re.security.validator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.security.dto.ErrorResponse;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class Validator {

    private final org.springframework.validation.Validator validator;

    public List<ErrorResponse> validate(Object object) {
        BindingResult bindingResult = new BeanPropertyBindingResult(object, object.getClass().getName());
        validator.validate(object, bindingResult);
        if (bindingResult.hasErrors()) {
            log.error("Binding result has: {} errors", bindingResult.getFieldErrors().size());
            return bindingResult.getFieldErrors()
                    .stream()
                    .map(ErrorResponse::new)
                    .toList();
        }
        return Collections.emptyList();
    }

}
