package com.Order.Order.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClentConfig {


    @Bean
    public WebClient inventoryWebClient() {
        return WebClient.builder().baseUrl("http://localhost:8081/api/v1").build();
    }

    @Bean
    public WebClient productWebClient() { 
        return WebClient.builder().baseUrl("http://localhost:8080/api/v1").build();
    }
}
