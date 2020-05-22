package com.sender.sparta.web.controller;

import com.sender.sparta.core.vo.ResponseModel;
import com.sender.sparta.service.SMSService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Api(tags = "验证码")
@Validated
@RestController
@RequestMapping("codes")
@AllArgsConstructor
public class CodeController {

    private final SMSService smsService;

    @GetMapping("sms")
    @ApiOperation("获取短信验证码")
    public Object sms(
            @ApiParam("手机号")
            @RequestParam
            @NotBlank
            @Pattern(regexp = "^1[0-9]{10}$", message = "手机号格式不正确") String mobile) {
        return ResponseModel.ok(smsService.send(mobile));
    }
}
