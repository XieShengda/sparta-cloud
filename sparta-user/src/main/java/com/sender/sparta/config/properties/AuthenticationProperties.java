package com.sender.sparta.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "authentication")
public class AuthenticationProperties {
    private Boolean enableMultiLogin;
}
