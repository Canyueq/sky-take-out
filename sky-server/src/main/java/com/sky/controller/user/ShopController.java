package com.sky.controller.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.result.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;


@RestController("userShopController")
@Slf4j
@RequestMapping("/user/shop")
@Api(tags = "店铺相关接口")
public class ShopController {
    @Autowired
    public RedisTemplate redisTemplate;

    // /**
    //  * 设置店铺状态
    //  * @param status
    //  * @return
    //  */
    // @PutMapping("{status}")
    // @ApiOperation("设置店铺状态")
    // public Result setStatus(@PathVariable Integer status) {
    //     //TODO: process PUT request
    //     redisTemplate.opsForValue().set("SHOP_STATUS",status);
    //     return Result.success();
    // }

    /**
     * 获取店铺状态
     * @return
     */
    @GetMapping("status")
    @ApiOperation("获取店铺状态")
    public Result<Integer> getStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get("SHOP_STATUS");
        return Result.success(status);
    }
    
}
