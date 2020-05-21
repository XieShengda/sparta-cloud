package com.sender.sparta.util;

import com.google.common.net.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class RequestHolder {
    public static String clientIP() {
        HttpServletRequest request = getInstance();
        String ip = request.getHeader(HttpHeaders.X_FORWARDED_FOR);
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static HttpServletRequest getInstance() {
        return ((ServletRequestAttributes) Objects.requireNonNull(
                RequestContextHolder.getRequestAttributes())).getRequest();
    }
}
