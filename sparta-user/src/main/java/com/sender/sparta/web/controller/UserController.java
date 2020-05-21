package com.sender.sparta.web.controller;

import com.sender.sparta.service.SecurityProxy;
import com.sender.sparta.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Api("用户")
@RestController
@RequestMapping("users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("login")
    @ApiOperation("短信登录")
    public Object login(@NotBlank @Pattern(regexp = "^1[0-9]{10}$", message = "手机号格式不正确") @ApiParam("手机号") String mobile,
                        @NotBlank @Size(max = 6, message = "短信验证码长度不符") @ApiParam("验证码") String validCode,
                        @NotBlank @ApiParam("消息id") String msgId) {
        return ResponseEntity.ok(userService.login(mobile, validCode, msgId));
    }

    @DeleteMapping("logout")
    @ApiOperation("退出登录")
    public Object logout() {
        UserDetails userDetails = SecurityProxy.userDetail();
        userService.logout(userDetails);
        return ResponseEntity.ok();
    }

}
