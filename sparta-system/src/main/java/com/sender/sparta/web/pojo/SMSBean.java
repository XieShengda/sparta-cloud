package com.sender.sparta.web.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SMSBean {
    private String msgId;
}
