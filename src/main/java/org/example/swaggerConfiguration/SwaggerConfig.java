package org.example.swaggerConfiguration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info().title("Alumini Application Service"))
                .addSecurityItem(new SecurityRequirement().addList("AluminiApplicationScheme"))
                .components(new Components().addSecuritySchemes("AluminiApplicationScheme", new SecurityScheme()));
  //              .name("Alumini Application").type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));

    }
}

// Swagger URL :- http://localhost:8080/swagger-ui/index.html