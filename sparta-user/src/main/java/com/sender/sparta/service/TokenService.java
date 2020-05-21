package com.sender.sparta.service;

import com.sender.sparta.web.pojo.TokenBean;
import org.springframework.security.core.userdetails.UserDetails;

public interface TokenService {

    TokenBean create(UserDetails userDetails);

    void remove(UserDetails userDetails);

    void remove();

    void removeCache(String token);

}
