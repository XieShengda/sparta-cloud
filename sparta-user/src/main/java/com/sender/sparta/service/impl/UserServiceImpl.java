package com.sender.sparta.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sender.sparta.core.exception.ApiException;
import com.sender.sparta.core.exception.ConflictException;
import com.sender.sparta.persistent.dao.SpartaUserMapper;
import com.sender.sparta.persistent.po.SpartaUser;
import com.sender.sparta.core.service.RedisService;
import com.sender.sparta.service.TokenService;
import com.sender.sparta.service.UserService;
import com.sender.sparta.core.util.RequestHolder;
import com.sender.sparta.web.dto.LoginDTO;
import com.sender.sparta.wrapper.UserWrapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final TokenService tokenService;
    private final RedisService redisService;
    private final SpartaUserMapper spartaUserMapper;

    @Override
    public Object login(LoginDTO body) {

        if (!verify(body.getMobile(), body.getValidCode(), body.getMsgId())) {
            ConflictException.abort();
        }
        // 判断手机号是否注册
        SpartaUser spartaUser = spartaUserMapper.selectOne(Wrappers.<SpartaUser>lambdaQuery().eq(SpartaUser::getMobile, body.getMobile()));
        if (spartaUser == null) {
            // 插入用户
            String inviteCode = RandomUtil.randomStringUpper(6);
            while (spartaUserMapper.selectOne(Wrappers.<SpartaUser>lambdaQuery().eq(SpartaUser::getInviteCode, inviteCode)) != null) {
                inviteCode = RandomUtil.randomStringUpper(6);
            }
            spartaUser = new SpartaUser()
                    .setNickname("用户".concat(RandomUtil.randomStringUpper(6)))
                    .setMobile(body.getMobile())
                    .setInviteCode(inviteCode)
                    .setLastLoginTime(new Date())
                    .setLastLoginIp(RequestHolder.clientIP());
            spartaUser.insert();
        } else {
            // 更新登录信息
            spartaUser.setLastLoginTime(new Date());
            spartaUser.setLastLoginIp(RequestHolder.clientIP());
            spartaUser.updateById();
        }

        return tokenService.create(new UserWrapper(spartaUser));
    }

    private boolean verify(String mobile, String validCode, String msgId) {
        String key = mobile.concat(msgId);
        if (!validCode.equals(redisService.get(key))) {
            ApiException.abort("短信验证码有误");
        }
        return redisService.remove(key);
    }

    @Override
    public void logout(UserDetails userDetails) {
        tokenService.remove();
    }
}
