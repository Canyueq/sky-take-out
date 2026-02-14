package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.sky.entity.Dish;

@Mapper
public interface SetmealMapper {

    @Select("select count(id) from setmeal where category_id=#{categoryId}")
    Integer getByCategoryId(long categoryId);
}
