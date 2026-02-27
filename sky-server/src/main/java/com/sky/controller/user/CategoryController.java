package com.sky.controller.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.service.impl.CategoryServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;


@RestController("userCategoryController")
@RequestMapping("/user/category")
@Api(tags = "用户端分类相关接口")
@Slf4j
public class CategoryController {
    private final CategoryServiceImpl categoryServiceImpl;
    public CategoryController(CategoryServiceImpl categoryServiceImpl){
        this.categoryServiceImpl = categoryServiceImpl;
    }
    
    /**
     * 根据类型查询分类
     * @param type
     * @return
     */
    @GetMapping("list")
    @ApiOperation("根据类型查询分类")
    public Result<Category[]> getCategoryList(Integer type) {
        log.info("套餐分类类型,{}",type);
        Category[] category = categoryServiceImpl.selectByType(type);
        return Result.success(category);
    }
    
}
