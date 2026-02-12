package com.sky.service.impl;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.service.DishService;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;;

@Service
public class DishServiceImpl implements DishService{
    
    private final DishMapper dishMapper;
    private final DishFlavorMapper dishFlavorMapper;
    public DishServiceImpl(DishMapper dishMapper,DishFlavorMapper dishFlavorMapper){
        this.dishMapper = dishMapper;
        this.dishFlavorMapper = dishFlavorMapper;
    }

    /**
     * 新增菜品
     * @param dishDto
     * @return
     */
    //因为会操作两张表需要保持原子性，一个失败全部失败
    @Transactional
    public void add(DishDTO dishDTO){
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.add(dish);

        //获取insert语句生成的主键值
        Long dishId = dish.getId();
        // System.out.printf("生成的id,%s",dishId);

        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors != null && flavors.size()>0){
            // System.out.printf("生成的id2,%s",dishId);
            //向口味表中插入数据,sql支持批量插入
            // for(int i=0;flavors[i];i++){
            //     dishFlavorMapper.insert(flavors[i]);
            // }
            flavors.forEach(item -> {
                item.setDishId(dishId);
            });
            dishFlavorMapper.insert(flavors);
        }
    };
    
    /**
     * 修改菜品
     * @param dishDTO
     * @return 
     */
    public void update(DishDTO dishDTO){
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.update(dish);
        List<DishFlavor> flavors = dishDTO.getFlavors();
    }
}
