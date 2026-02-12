package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.sky.annotation.AutoFill;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;

@Mapper
public interface DishMapper {

     @Select("select count(id) from dish where category_id=#{categoryId}")
     Integer getByCategoryId(long categoryId);

     /**
     * 新增菜品
     * @param dishDto
     * @return
     */
     @AutoFill(OperationType.INSERT)
     void add(Dish dish);

     /**
     * 修改菜品
     * @param dish
     * @return
     */
     @AutoFill(value = OperationType.UPDATE)
     void update(Dish dish);
}
