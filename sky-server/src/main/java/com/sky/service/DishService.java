package com.sky.service;

import com.sky.dto.DishDTO;

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
}
