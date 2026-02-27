package com.sky.controller.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.properties.JwtProperties;
import com.sky.result.Result;
import com.sky.service.impl.UserServiceImpl;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserLoginVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@Api(tags = "用户相关接口")
@Slf4j
@RequestMapping("/user/user")
public class UserController {
    private final UserServiceImpl userServiceImpl;
    private final JwtProperties jwtProperties;
    public UserController(UserServiceImpl userServerImpl,JwtProperties jwtProperties){
        this.userServiceImpl = userServerImpl;
        this.jwtProperties = jwtProperties;
    }

    /**
     * 微信登录
     * @param userLoginDTO
     * @return
     */
    @PostMapping("login")
    @ApiOperation("微信登录")
    public Result<UserLoginVO> login(UserLoginDTO userLoginDTO) {
        log.info("微信登录,{}",userLoginDTO);
        //TODO: process POST request
         User user = userServiceImpl.login(userLoginDTO);

         Map<String,Object> claims = new HashMap<>();
        
         claims.put(JwtClaimsConstant.USER_ID,user.getId());
         String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(),jwtProperties.getUserTtl(),claims);
         UserLoginVO userLoginVO = UserLoginVO.builder().id(user.getId()).openid(user.getOpenid()).token(token).build();
        return Result.success(userLoginVO);
    }
    
}
