package com.sky.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.Result;
import com.sky.service.impl.DishServiceImpl;
import com.sky.service.impl.SetmealServiceImpl;
import com.sky.vo.DishItemVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController("userSetmealController")
@Api(tags = "用户端套餐相关接口")
@Slf4j
@RequestMapping("/user/setmeal")
public class SetmealController {
    @Autowired
    private SetmealServiceImpl setmealServiceImpl;
    
    /**
     * 根据分类id查询套餐列表
     * @param categoryId
     * @return
     */
    @GetMapping("list")
    @ApiOperation("根据分类id查询套餐列表")
    public Result<List<Setmeal>> getByCategoryId(Long categoryId) {
        List<Setmeal>setmeal = setmealServiceImpl.getByCategoryId(categoryId);
        return Result.success(setmeal);
    }

    /**
     * 根据套餐id查询菜品
     * @param categoryId
     * @return
     */
    @GetMapping("/dish/{id}")
    @ApiOperation("根据套餐id查询菜品")
    public Result<List<DishItemVO>> getBySetmealID(@PathVariable("id") Long setmealId) {
        List<DishItemVO> dishItemVOs = setmealServiceImpl.getDishItemById(setmealId);
        return Result.success(dishItemVOs);
    }
    
}
