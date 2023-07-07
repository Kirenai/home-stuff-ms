package me.kirenai.re.role;

import me.kirenai.re.exception.ExceptionApplication;
import me.kirenai.re.security.SecurityApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(value = {SecurityApplication.class, ExceptionApplication.class})
@SpringBootApplication
public class RoleApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoleApplication.class, args);
    }

}