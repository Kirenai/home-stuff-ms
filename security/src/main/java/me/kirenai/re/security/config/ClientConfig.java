package me.kirenai.re.security.config;

import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder webClientBuilder,
                               ReactorLoadBalancerExchangeFilterFunction loadBalancerExchangeFilterFunction) {
        return webClientBuilder.filter(loadBalancerExchangeFilterFunction).build();
    }

}