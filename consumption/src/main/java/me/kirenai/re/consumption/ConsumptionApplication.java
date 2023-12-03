package me.kirenai.re.consumption;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "me.kirenai.re.consumption",
        "me.kirenai.re.security",
        "me.kirenai.re.exception",
})
public class ConsumptionApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumptionApplication.class, args);
    }

}
