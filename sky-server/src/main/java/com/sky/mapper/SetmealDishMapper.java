package com.sky.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;



@Mapper
public interface SetmealDishMapper {
    /**
     * 通过菜品id查询套餐
     * @param dishIds
     * @return
     */
    List<Long> selectByDishId(List<Long> dishIds);
}
