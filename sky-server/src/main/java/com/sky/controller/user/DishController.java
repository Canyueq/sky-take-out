package com.sky.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.impl.DishServiceImpl;
import com.sky.vo.DishVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.GetMapping;


@RestController("userDishController")
@RequestMapping("/user/dish")
@Api(tags = "用户端菜品相关接口")
public class DishController {
    @Autowired
    private DishServiceImpl dishServiceImpl;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 根据id查询分类
     * @param categoryId
     * @return
     */
    @GetMapping("list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<DishVO>> list(Long categoryId) {
        //构造redis的key,规则dish_[分类id]
        String key = "dish_"+categoryId;
        //查询是否存在菜品数据
        List<DishVO> list = (List<DishVO>) redisTemplate.opsForValue().get(key);
        if(list!=null && list.size()>0){
            return Result.success(list);
        }
        //如果不存在就存入数据库
        Dish dish = Dish.builder().categoryId(categoryId).status(StatusConstant.ENABLE).build();
        list = dishServiceImpl.ListwthFlavors(dish);
        //并且存入cache缓
        redisTemplate.opsForValue().set(key,list);
        return Result.success(list);
    }
    
}
