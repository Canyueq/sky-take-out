package com.sky.service;

import java.util.List;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

public interface DishService {
    /**
     * 新增菜品
     * @param dishDto
     * @return
     */
    public void add(DishDTO dishDTO);

    /**
     * 修改菜品
     * @param dishDto
     * @return
     */
    public void update(DishDTO dishDTO);

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    public PageResult page(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 通过id删除菜品
     * @param id
     */
    public void deleteById(List<Long> id);

    /**
     * 根据id查询菜品
     * @param id
     * @return
     */
    public DishVO getById(long id);

    /**
     * 根据分类id查询菜品
     * @param id
     * @return
     */
    public Integer getByCategoryId(long id) ;
    
    /**
     * 修改菜品状态
     * @param status
     * @param id
     * @return
     */
    public void setStatus(Integer status,long id);
}
