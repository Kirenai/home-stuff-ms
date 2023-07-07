package me.kirenai.re.nourishment;

import me.kirenai.re.exception.ExceptionApplication;
import me.kirenai.re.security.SecurityApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(value = {SecurityApplication.class, ExceptionApplication.class})
@SpringBootApplication
public class NourishmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(NourishmentApplication.class, args);
    }

}