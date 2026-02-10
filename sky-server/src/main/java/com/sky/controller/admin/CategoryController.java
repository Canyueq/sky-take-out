package com.sky.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;

import com.sky.service.CategoryService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "分类相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
    *分类管理分页查询
    * @param categoryPageQueryDTO
    * @return
    */
   @GetMapping("page")
   @ApiOperation("分类管理分页查询")
   public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO) {
       log.info("category page query:{}", categoryPageQueryDTO);

       PageResult pageResult = categoryService.page(categoryPageQueryDTO);

       return Result.success(pageResult);
   }

    /**
    * 修改分类
     * @param categoryPageQueryDTO
     * @return
    */
    @PutMapping
    @ApiOperation("修改分类")
    public Result update(Category category) {
        //TODO: process POST request
        categoryService.update(category);
        return Result.success();
    }

    /**
    * 新增分类
     * @param category
     * @return
    */
    @PostMapping
    @ApiOperation("新增分类")
    public Result insert(@RequestBody CategoryDTO categoryDto){
        categoryService.insert(categoryDto);
        return Result.success();
    }
    
    /**
    * 启用禁用分类
     * @param status
     * @param id
     * @return
    */
    @PostMapping("/status/{status}")
    @ApiOperation("启用禁用分类")
    public Result setStatus(@PathVariable Integer status, Long id){
        categoryService.setStatus(status,id);
        return Result.success();
    }

    /**
    * 根据id删除分类
     * @param id
     * @return
    */
    @DeleteMapping
    @ApiOperation("根据id删除分类")
    public Result deleteById(Long id){
        log.info("删除的id,{}",id);
        categoryService.deleteById(id);
        return Result.success();
    }
}
