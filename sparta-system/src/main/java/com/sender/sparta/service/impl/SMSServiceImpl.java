package com.sender.sparta.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.sender.sparta.config.properties.SMSProperties;
import com.sender.sparta.core.config.properties.ProfilesProperties;
import com.sender.sparta.core.constant.ProfilesEnum;
import com.sender.sparta.core.service.RedisService;
import com.sender.sparta.service.SMSService;
import com.sender.sparta.web.pojo.SMSBean;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@EnableConfigurationProperties({ProfilesProperties.class, SMSProperties.class})
public class SMSServiceImpl implements SMSService {

    private final RedisService redisService;
    private final ProfilesProperties profilesProperties;
    private final SMSProperties smsProperties;

    @Override
    public Object send(String mobile) {

        String code, msgId;
        if (ProfilesEnum.inProduction(profilesProperties.getActive())) {
            code = RandomUtil.randomNumbers(4);
            msgId = requestSMS(mobile, code);
        } else {
            code = smsProperties.getDevCode();
            msgId = smsProperties.getDevMsgId();
        }

        redisService.set(mobile.concat(msgId), code, smsProperties.getExpireMinutes() * 60);

        return new SMSBean().setMsgId(msgId);
    }

    private String requestSMS(String mobile, String code) {
        // TODO: 5/22/20 短信接入
        return "000";
    }
}
