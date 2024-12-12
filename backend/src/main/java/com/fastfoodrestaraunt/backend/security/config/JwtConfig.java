package com.fastfoodrestaraunt.backend.security.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "security.jwt")
public class JwtConfig {
    private final String secret;
    private final String algorithm;
    private final String accessExpiration;
    private final String refreshExpiration;
}
