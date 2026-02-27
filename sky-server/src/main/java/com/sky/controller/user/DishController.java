package com.sky.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    
    /**
     * 根据id查询分类
     * @param categoryId
     * @return
     */
    @GetMapping("list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<DishVO>> getCategoryList(Long categoryId) {
        List<DishVO> dish = dishServiceImpl.ListwthFlavors(categoryId);
        return Result.success(dish);
    }
    
}
