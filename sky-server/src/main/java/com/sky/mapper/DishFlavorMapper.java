package com.sky.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sky.entity.DishFlavor;

@Mapper
public interface DishFlavorMapper {

    /**
     * 批量插入口味
     * @param dishFlavor
     */
    void insert(List<DishFlavor> dishFlavors);
}
