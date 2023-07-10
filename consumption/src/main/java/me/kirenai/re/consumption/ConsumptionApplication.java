package me.kirenai.re.consumption;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication(scanBasePackages = {
        "me.kirenai.re.consumption",
        "me.kirenai.re.security",
        "me.kirenai.re.exception",
})
@PropertySources({
        @PropertySource("classpath:security-default.properties")
})
public class ConsumptionApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumptionApplication.class, args);
    }

}
