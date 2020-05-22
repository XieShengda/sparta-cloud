package com.sender.sparta.web.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class LoginDTO {

    @ApiModelProperty("手机号")
    @NotBlank
    @Pattern(regexp = "^1[0-9]{10}$", message = "手机号格式不正确")
    private String mobile;

    @ApiModelProperty("验证码")
    @NotBlank
    @Size(max = 6, message = "短信验证码长度不符")
    private String validCode;

    @ApiModelProperty("消息id")
    @NotBlank
    private String msgId;
}
