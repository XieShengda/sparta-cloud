package com.sender.sparta.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限信息访问代理
 */
public class SecurityProxy {

    /**
     * 用户信息
     */
    public static UserDetails userDetail() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 获取用户所有权限
     */
    public static List<GrantedAuthority> authorities() {
        return new ArrayList<>(userDetail().getAuthorities());
    }

}
