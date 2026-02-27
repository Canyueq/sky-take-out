package com.sky.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.sky.service.CategoryService;

import ch.qos.logback.classic.pattern.Util;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.implementation.bytecode.Throw;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.Page;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Setmeal;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import org.springframework.beans.BeanUtils;

@Service
public class CategoryServiceImpl implements CategoryService {

    //获取Mapper接口的实例对象
    @Autowired
    private CategoryMapper categoryMapper;
    private  final SetmealMapper setmealMapper;
    private final DishMapper dishMapper;
    public CategoryServiceImpl(
    CategoryMapper categoryMapper,
    SetmealMapper setmealMapper,
    DishMapper dishMapper
) {
    this.categoryMapper = categoryMapper;
    this.setmealMapper = setmealMapper;
    this.dishMapper = dishMapper;
}

    /**
    *分类管理分页查询
    * @param categoryPageQueryDTO
    * @return
    */
    public PageResult page(CategoryPageQueryDTO categoryPageQueryDTO) {
        int page = categoryPageQueryDTO.getPage();
        int size = categoryPageQueryDTO.getPageSize();
        PageHelper.startPage(page, size);
        Page<Category> category = categoryMapper.pageQuery(categoryPageQueryDTO);
        return new PageResult(category.getTotal(), category.getResult());
    }
    
    /**
    * 修改分类
     * @param categoryPageQueryDTO
     * @return
    */
    @PostMapping
    public void update(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(BaseContext.getCurrentId());
        categoryMapper.update(category);
    }

    /**
    * 新增分类
     * @param category
     * @return
    */
    public void insert(CategoryDTO categoryDTO){
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        // category.setCreateTime(LocalDateTime.now());
        // category.setUpdateTime(LocalDateTime.now());
        // Long id = BaseContext.getCurrentId();
        // category.setCreateUser(id);
        // category.setUpdateUser(id);
        category.setStatus(StatusConstant.ENABLE);
        categoryMapper.insert(category);
    }

    /**
    * 启用禁用分类
     * @param status
     * @param id
     * @return
    */
    public void setStatus(Integer status, Long id){
        Category category = new Category();
        category.setId(id);
        category.setStatus(status);
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(BaseContext.getCurrentId());
        categoryMapper.update(category);
    }

    /**
    * 根据id删除分类
     * @param id
     * @return
    */
    public void deleteById(Long id){
        List<Setmeal> setmeal = setmealMapper.getByCategoryId(id);
        if(setmeal.size() > 0){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }
        Integer count = dishMapper.getByCategoryId(id);
        if(count > 0){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }
        categoryMapper.deleteById(id);
    }

    /** 
    * 根据类型查询分类
     * @param type
     * @return
    */
    public Category[] selectByType(Integer type){
        Category[] categories = categoryMapper.selectByType(type);
        return categories;
    }
}
