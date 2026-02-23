package com.sky.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import com.sky.entity.SetmealDish;
import com.sky.vo.DishItemVO;



@Mapper
public interface SetmealDishMapper {

    /**
     * 通过菜品id查询套餐
     * @param dishIds
     * @return
     */
    List<Long> selectByDishId(List<Long> dishIds);

    /**
     * 向套餐中新增菜品
     * @param dishList
     */
    void add(List<SetmealDish> dishList);
    
    /**
     * 根据套餐id删除对应的菜品
     * @param setmealId
     */
    @Delete("delete from setmeal_dish where setmeal_id=#{setmealId}")
    void deleteBySetmealId(Long setmealId);

    /**
     * 根据套餐ids批量删除菜品
     * @param ids
     */
    void deleteBySetmealIds(List<Long> ids);

    /**
     * 根据套餐id查询菜品
     * @param setmealId
     * @return
     */
    List<SetmealDish> getBySetmealId(Long setmealId);
}
