package com.sender.sparta.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = MetaProperties.PREFIX)
public class MetaProperties {
    public static final String PREFIX = "meta";
    private Long shardId;
    private Long maxNext;
}
