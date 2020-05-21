package com.sender.sparta.web.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TokenBean {
    private String token;
    private String secretKey;
}
