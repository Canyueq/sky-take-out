package com.sky.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.utils.HttpClientUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl {
    //微信服务接口地址
    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";
    private final UserMapper userMapper;
    public final WeChatProperties weChatProperties;
    public UserServiceImpl(UserMapper userMapper,WeChatProperties weChatProperties){
        this.userMapper = userMapper;
        this.weChatProperties = weChatProperties;
    }

    private String getOpenid(String code){
        //调用微信接口获取openid
        Map<String,String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret",weChatProperties.getSecret());
        map.put("js_code",code);
        map.put("grant_type","authorization_code");
        String json = HttpClientUtil.doGet(WX_LOGIN, map);
        log.info("json,{}",json);
        //判断openid是否为空
        JSONObject jsonObject = JSON.parseObject(json);
        String openid = jsonObject.getString("openid");
        return openid;
    }

    /**
     * 微信登录
     * @param userLoginDTO
     * @return
     */
    @Transactional
    public User login(UserLoginDTO userLoginDTO){
        log.info("登录信息,{}",userLoginDTO.getCode());
        //调用微信接口获取openid
        // Map<String,String> map = new HashMap<>();
        // map.put("appid", weChatProperties.getAppid());
        // map.put("secret",weChatProperties.getSecret());
        // map.put("js_code",userLoginDTO.getCode());
        // map.put("grant_type","authorization_code");
        // String json = HttpClientUtil.doGet(WX_LOGIN, map);
        // //判断openid是否为空
        // JSONObject jsonObject = JSON.parseObject(json);
        // String openid = jsonObject.getString("openid");
        String openid = getOpenid(userLoginDTO.getCode());
        if(openid == null){
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        //判断是否为新用户
        User user = userMapper.getByopenid(openid);
        //如果是新用户自动注册
        if(user == null){
            user = User.builder()
                            .openid(openid)
                            .createTime(LocalDateTime.now())
                            .build();
            userMapper.insert(user);
        }
        //返回用户对象
        return user;
    }
}
