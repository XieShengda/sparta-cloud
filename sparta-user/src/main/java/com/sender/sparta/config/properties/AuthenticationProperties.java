package com.sender.sparta.config.properties;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Accessors(fluent = true)
@Configuration
@ConfigurationProperties(prefix = AuthenticationProperties.PREFIX)
public class AuthenticationProperties {
    public final static String PREFIX = "authentication";

    private Boolean enableMultiLogin;
}
