package com.sender.sparta.config;

import com.sender.sparta.constant.TokenHashKey;
import com.sender.sparta.constant.TokenKey;
import com.sender.sparta.core.service.RedisService;
import com.sender.sparta.security.constant.TokenConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.concurrent.TimeUnit;

@Configuration
public class UserSecurityConfig {
    @Bean
    public UserDetailsService userDetailsService(RedisService redisService) {
        return token -> {
            // TODO: 5/21/20 更新用户登录信息 ip，时间，平台等
            String username = (String) redisService.hGet(token, TokenHashKey.USERNAME, TokenConfig.TOKEN_EXPIRED_DAYS, TimeUnit.DAYS);
            if (username == null) {
                return null;
            }

            return (UserDetails) redisService.get(TokenKey.USER_PREFIX.concat(username));
        };
    }
}
