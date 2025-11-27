package com.smartpark.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI smartParkOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SmartPark (ArrayList) API")
                        .version("1.0.0")
                        .description("In-memory ArrayList implementation of SmartPark")
                        .contact(new Contact().name("SmartPark Dev")));
    }
}
