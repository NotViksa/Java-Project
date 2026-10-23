package com.tu.javaproject.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI hotelReservationsOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Hotel Reservations API")
                .version("1.0")
                .description("REST API for hotels, rooms, extras, seasons, guests and reservations."));
    }
}
