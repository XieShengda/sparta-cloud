package com.sender.sparta.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.text.MessageFormat;

@Data
@Primary
@Configuration
@ConfigurationProperties(prefix = "sms")
public class SMSProperties {
    private String title;
    private String pattern;
    private String devCode;
    private String devMsgId;
    private Long expireMinutes;

    public String message(String code) {
        return MessageFormat.format(pattern, code);
    }
}
