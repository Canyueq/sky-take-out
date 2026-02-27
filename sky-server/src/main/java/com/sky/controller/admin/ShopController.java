package com.sky.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.result.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;



@RestController("adminShopController")
@Slf4j
@RequestMapping("/admin/shop")
@Api(tags = "店铺相关接口")
public class ShopController {
    @Autowired
    public RedisTemplate redisTemplate;

    public static final String key = "SHOP_STATUS";
    /**
     * 设置店铺状态
     * @param status
     * @return
     */
    @PutMapping("{status}")
    @ApiOperation("设置店铺状态")
    public Result setStatus(@PathVariable Integer status) {
        //TODO: process PUT request
        log.info("店铺设置的状态,{}",status);
        redisTemplate.opsForValue().set(key,status);
        return Result.success(); 
    }
    
    /**
     * 获取店铺状态
     * @return
     */
    @GetMapping("status")
    @ApiOperation("获取店铺状态")
    public Result<Integer> getStatus() {
        log.info("店铺的状态,{}");
        Integer status = (Integer) redisTemplate.opsForValue().get(key);
        return Result.success(status);
    }
    
}
