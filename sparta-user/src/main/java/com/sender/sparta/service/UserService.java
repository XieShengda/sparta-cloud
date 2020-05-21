package com.sender.sparta.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    Object login(String mobile, String validCode, String msgId);

    void logout(UserDetails userDetails);

}
