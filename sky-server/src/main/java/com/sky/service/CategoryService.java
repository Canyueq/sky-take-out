package com.sky.service;

import com.sky.entity.Category;

import org.springframework.web.bind.annotation.GetMapping;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;

import io.swagger.annotations.ApiOperation;

public interface CategoryService {

    /**
    *分类管理分页查询
    * @param categoryPageQueryDTO
    * @return
    */
    public PageResult page(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
    * 修改分类
     * @param category
     * @return
    */
    public void update(Category category) ;

    /**
    * 新增分类
     * @param category
     * @return
    */
    public void insert(CategoryDTO categoryDTO) ;

    /**
    * 启用禁用分类
     * @param status
     * @param id
     * @return
    */
    public void setStatus(Integer status, Long id);

    /**
    * 根据id删除分类
     * @param id
     * @return
    */
    public void deleteById(Long id);

}
