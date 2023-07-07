package me.kirenai.re.exception;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExceptionApplication {

    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }

}
