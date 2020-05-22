package com.sender.sparta.core.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "meta")
public class MetaProperties {
    private Long shardId;
    private Long maxNext;
}
