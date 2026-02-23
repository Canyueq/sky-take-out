package com.sky.controller.admin;

import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.service.impl.DishServiceImpl;
import com.sky.vo.DishVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@Slf4j
@RequestMapping("/admin/dish")
@Api(tags="菜品相关接口")
public class DishController {

    private final DishServiceImpl dishServiceImpl;

    private final DishService dishesService;
    public DishController(DishService dishesService, DishServiceImpl dishServiceImpl){
        this.dishesService = dishesService;
        this.dishServiceImpl = dishServiceImpl;
    }

    /**
     * 新增菜品
     * @param dishDto
     * @return
     */
    @PostMapping
    @ApiOperation("新增菜品")
    public Result add(@RequestBody DishDTO dishDTO){
        log.info("新增菜品,{}",dishDTO);
        dishesService.add(dishDTO);
        return Result.success();
    }

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("page")
    @ApiOperation("菜品分页查询")    
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO){
        log.info("查询的菜品,{}",dishPageQueryDTO);
        PageResult pageResult =  dishServiceImpl.page(dishPageQueryDTO);
        log.info("查询结果,{}",pageResult);
        return Result.success(pageResult);
    }

    /**
     * 通过id删除菜品
     * @param id
     */
    @DeleteMapping
    @ApiOperation("通过id删除菜品")
    public Result deleteById(@RequestParam List<Long> ids){
        log.info("菜品批量删除,{}",ids);
        dishServiceImpl.deleteById(ids);
        return Result.success();
    }

    /**
     * 修改菜品
     * @param dishDTO
     * @return
     */
    @PutMapping
    @ApiOperation("修改菜品")
    public Result update(@RequestBody DishDTO dishDTO) {
        //TODO: process PUT request
        dishServiceImpl.update(dishDTO);
        return Result.success();
    }

    /**
     * 根据id查询菜品
     * @param id
     * @return
     */
    @GetMapping("{id}")
    @ApiOperation("根据id查询菜品")
    public Result<DishVO> getById(@PathVariable("id") Long id) {
        log.info("参数,{}",id);
        DishVO dishVO = dishServiceImpl.getById(id);
        return Result.success(dishVO);
    }

    /**
     * 根据分类id查询菜品
     * @param id
     * @return
     */
    @GetMapping("list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<Dish>> getByName(String name) {
        List<Dish> dish = dishServiceImpl.getByCategoryId(name);
        return Result.success(dish);
    }
    
    /**
     * 修改菜品状态
     * @param status
     * @param id
     * @return
     */
    @PostMapping("status/{status}")
    @ApiOperation("修改菜品状态")
    public Result setStatus(@PathVariable Integer status,Long id) {
        log.info("参数,{}",status,id);
        //TODO: process POST request
        dishServiceImpl.setStatus(status,id);
        return Result.success();
    }
    
}
