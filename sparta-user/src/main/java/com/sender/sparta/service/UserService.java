package com.sender.sparta.service;

import com.sender.sparta.web.dto.LoginDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    Object login(LoginDTO body);

    void logout(UserDetails userDetails);

}
