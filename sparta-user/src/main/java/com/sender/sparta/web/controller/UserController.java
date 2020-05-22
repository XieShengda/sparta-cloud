package com.sender.sparta.web.controller;

import com.sender.sparta.core.vo.ResponseModel;
import com.sender.sparta.security.service.SecurityProxy;
import com.sender.sparta.service.UserService;
import com.sender.sparta.web.dto.LoginDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "用户")
@RestController
@RequestMapping("users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("login")
    @ApiOperation("短信登录")
    public Object login(@RequestBody @Valid LoginDTO body) {
        return ResponseModel.ok(userService.login(body));
    }

    @DeleteMapping("logout")
    @ApiOperation("退出登录")
    public Object logout() {
        UserDetails userDetails = SecurityProxy.userDetail();
        userService.logout(userDetails);
        return ResponseModel.ok();
    }

}
