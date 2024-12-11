package com.fastfoodrestaraunt.backend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Fast food restaurant API documentation",
                description = "API of backend part of fast food restaurant.",
                version = "1.0.0"
        )
)
public class OpenApiConfig {
}
