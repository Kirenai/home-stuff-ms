package me.kirenai.re.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication(scanBasePackages = {
        "me.kirenai.re.auth",
        "me.kirenai.re.security",
})
@PropertySources({
        @PropertySource("classpath:security-default.properties")
})
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}