package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishItemVO;
import com.sky.vo.DishVO;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class DishServiceImpl implements DishService{
    
    private final DishMapper dishMapper;
    private final DishFlavorMapper dishFlavorMapper;
    private final SetmealDishMapper setmealDishMapper;
    public DishServiceImpl(DishMapper dishMapper,DishFlavorMapper dishFlavorMapper,SetmealDishMapper setmealDishMapper){
        this.dishMapper = dishMapper;
        this.dishFlavorMapper = dishFlavorMapper;
        this.setmealDishMapper = setmealDishMapper;
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
    @Transactional
    public void update(DishDTO dishDTO){
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.update(dish);
        List<DishFlavor> flavors = dishDTO.getFlavors();
        //先删除原有的口味，再添加防止id增加
        dishFlavorMapper.deleteByDishId(dishDTO.getId());
        if(flavors != null && flavors.size()>0){
            flavors.forEach(item -> {
                    item.setDishId(dishDTO.getId());
                });
        dishFlavorMapper.insert(flavors);
        }
    }

    /** 
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    public PageResult page(DishPageQueryDTO dishPageQueryDTO){
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<DishVO> dish = dishMapper.page(dishPageQueryDTO);
        return new PageResult(dish.getTotal(),dish.getResult());
    }

    /**
     * 通过id删除菜品
     * @param id
     */
    @Transactional
    public void deleteById(List<Long> ids){
        //不能删除启售中和关联套餐的菜品
        for(long id :ids){
            Dish dish = dishMapper.selectById(id);
            if(dish.getStatus() == StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        log.info("查询是否关联套餐,{}", ids);
        try {
            List<Long> setmealIds = setmealDishMapper.selectByDishId(ids);
            log.info("查询是否关联套餐, setmealIds: {}", setmealIds); // ✅ 会打印\
            if(setmealIds!=null && setmealIds.size()>0){
                throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
            }
        } catch (Exception e) {
            log.error("查询关联套餐异常", e); // ✅ 会打印异常堆栈
        }
        //删除菜品及关联口味
        // for(long id:ids){
        //     dishMapper.deleteById(id);
        //     dishFlavorMapper.deleteByDishId(id);
        // }
        //使用sql优化删除
        dishMapper.deleteByIds(ids);
        dishFlavorMapper.deleteByDishIds(ids);
    }
    /**
     * 根据id查询菜品
     * @param id
     * @return
     */
    public DishVO getById(long id) {
        Dish dish = dishMapper.getById(id);
        List<DishFlavor> dishFlavor  = dishFlavorMapper.getByDishId(id);
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavor);
        return dishVO;
    }

    /**
     * 根据分类名称查询菜品
     * @param name
     * @return
     */
    public List<Dish> getByCategoryName(String name) {
        List<Dish> dish = dishMapper.getListByCategoryName(name);
        return dish;
    }

    /**
     * 根据分类id查询菜品
     * @param id
     * @return
     */
    public List<Dish> getByCategoryId(Long id) {
        List<Dish> dish = dishMapper.getListByCategoryId(id);
        return dish;
    }
    
    /**
     * 修改菜品状态
     * @param status
     * @param id
     * @return
     */
    public void setStatus(Integer status,long id) {
        //TODO: process POST request
        Dish dish = Dish.builder().status(status).id(id).build();
        dishMapper.update(dish);
    }

    /**
     * 根据套餐id查询菜品
     * @param setmealId
     * @return
     */
        public List<SetmealDish> geBySetmealId(Long setmealId){
        List<SetmealDish> setmealDish = setmealDishMapper.getBySetmealId(setmealId);
        return setmealDish;
    }

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    public List<DishVO> ListwthFlavors(Long id){
        List<DishVO> dishVOList = new ArrayList<DishVO>();
        List<Dish> dish = dishMapper.getListByCategoryId(id);
        dish.forEach(item -> {  
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(item, dishVO);
            dishVO.setFlavors(dishFlavorMapper.getByDishId(item.getId()));
            dishVOList.add(dishVO);
        });
 
        return dishVOList;
    }

    
}
