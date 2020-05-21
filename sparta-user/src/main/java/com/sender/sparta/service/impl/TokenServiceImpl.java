package com.sender.sparta.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.sender.sparta.config.properties.AuthenticationProperties;
import com.sender.sparta.constant.TokenKey;
import com.sender.sparta.service.AsyncService;
import com.sender.sparta.service.RedisService;
import com.sender.sparta.service.TokenService;
import com.sender.sparta.util.RequestHolder;
import com.sender.sparta.web.pojo.TokenBean;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.sender.sparta.constant.TokenConfig.HEADER;
import static com.sender.sparta.constant.TokenConfig.TOKEN_EXPIRED_DAYS;
import static com.sender.sparta.constant.TokenHashKey.SECRET_KEY;
import static com.sender.sparta.constant.TokenHashKey.USERNAME;
import static com.sender.sparta.constant.TokenKey.USER_PREFIX;

/**
 * 登录：
 * - 禁止多设备登录：删除用户所有的token信息
 * 登出：
 * - 删除当前token的信息
 * 忘记密码：
 * - 删除用户所有的token的信息
 * token失效：
 * - 删除当前token的信息
 */
@Component
@AllArgsConstructor
@EnableConfigurationProperties(AuthenticationProperties.class)
public class TokenServiceImpl implements TokenService {

    private final RedisService redisService;
    private final AsyncService asyncService;
    private final AuthenticationProperties authenticationProperties;

    /**
     * 生成Token
     */
    @Override
    public TokenBean create(UserDetails userDetails) {
        if (!authenticationProperties.enableMultiLogin()) {
            remove(userDetails);
        }

        String token = TokenKey.TOKEN_PREFIX.concat(UUID.randomUUID().toString());
        String secretKey = RandomUtil.randomString(6);

        // token -> username
        redisService.hSet(token, USERNAME, userDetails.getUsername(), TOKEN_EXPIRED_DAYS, TimeUnit.DAYS);
        // token -> secret_key
        redisService.hSet(token, SECRET_KEY, secretKey);
        // tokens:username -> token_set
        redisService.sAdd(TokenKey.TOKEN_SET_PREFIX.concat(userDetails.getUsername()), token);
        // users:username -> UserDetails.class
        redisService.set(USER_PREFIX.concat(userDetails.getUsername()), userDetails);
        // token map to username
        redisService.hSet(TokenKey.TOKEN_USER_MAPPING, token, userDetails.getUsername());

        return new TokenBean()
                .setToken(token)
                .setSecretKey(secretKey);
    }

    /**
     * 删除用户所有token相关信息
     */
    @Override
    public void remove(UserDetails userDetails) {
        String tokenSetKey = TokenKey.TOKEN_SET_PREFIX.concat(userDetails.getUsername());
        Set<String> keys = (Set<String>) (Object) redisService.sMembers(tokenSetKey);
        redisService.hRemove(TokenKey.TOKEN_USER_MAPPING, keys.toArray());
        keys.add(tokenSetKey);
        redisService.remove(new ArrayList<>(keys));
    }

    /**
     * 删除当前token
     */
    @Override
    public void remove() {
        String token = getToken();
        redisService.remove(token);
        removeCache(token);
    }

    /**
     * 删除token相关的缓存
     */
    @Override
    public void removeCache(String token) {
        String username = (String) redisService.hGet(TokenKey.TOKEN_USER_MAPPING, token);
        redisService.sRemove(TokenKey.TOKEN_SET_PREFIX.concat(username), token);
        redisService.hRemove(TokenKey.TOKEN_USER_MAPPING, token);
    }

    /**
     * 获取当前token
     */
    private String getToken() {
        return RequestHolder.getInstance().getHeader(HEADER);
    }

}
