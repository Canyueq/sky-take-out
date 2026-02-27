package com.sky.service;

import java.util.List;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.SetmealDish;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
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
     * 根据分类名称查询菜品
     * @param id
     * @return
     */
    public List<Dish> getByCategoryName(String name) ;

    /**
     * 根据分类id查询菜品
     * @param id
     * @return
     */
    public List<Dish> getByCategoryId(Long id) ;
    
    /**
     * 修改菜品状态
     * @param status
     * @param id
     * @return
     */
    public void setStatus(Integer status,long id);

    /**
     * 根据套餐id查询菜品
     * @param setmealId
     * @return
     */
    public List<SetmealDish> geBySetmealId(Long setmealId);

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    public List<DishVO> ListwthFlavors(Long id);
}
