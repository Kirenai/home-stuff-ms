package me.kirenai.re.role;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication(scanBasePackages = {
        "me.kirenai.re.role",
        "me.kirenai.re.security",
        "me.kirenai.re.exception",
})
@PropertySources({
        @PropertySource("classpath:security-default.properties")
})
public class RoleApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoleApplication.class, args);
    }

}