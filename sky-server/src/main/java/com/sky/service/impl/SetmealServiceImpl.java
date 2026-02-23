package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

import lombok.var;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;;

@Slf4j
@Service
public class SetmealServiceImpl implements SetmealService{

    private final SetmealMapper setmealMapper;
    private final SetmealDishMapper setmealDishMapper;
    public SetmealServiceImpl(SetmealMapper setmealMapper,SetmealDishMapper setmealDishMapper){
        this.setmealMapper = setmealMapper;
        this.setmealDishMapper = setmealDishMapper;
    }

    /**
     * 分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    public PageResult page(SetmealPageQueryDTO setmealPageQueryDTO){
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());
        Page<Setmeal> setmeal = setmealMapper.page(setmealPageQueryDTO);
        // log.info("套餐分页查询结果,{}",setmeal);
        return new PageResult(setmeal.getTotal(),setmeal.getResult());
    }
    
    /**
     * 新增
     * @param setmealDTO
     */
    @Transactional
    public void add (SetmealDTO setmealDTO){
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.add(setmeal);
        var setmealId=setmeal.getId();
        List<SetmealDish> dish = setmealDTO.getSetmealDishes();
        if( dish!=null && dish.size()>0){
            dish.forEach(item -> item.setSetmealId(setmealId));
            setmealDishMapper.add(dish);
        }
    }

    /**
     * 编辑
     * @param setmealDTO
     */
    @Transactional
    public void update(SetmealDTO setmealDTO){
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.update(setmeal);
        var setmealDish = setmealDTO.getSetmealDishes();
        //先删除套餐中已有的菜品
        setmealDishMapper.deleteBySetmealId(setmealDTO.getId());
        if( setmealDish!=null && setmealDish.size()>0){
            setmealDish.forEach(item -> item.setSetmealId(setmealDTO.getId()));
            setmealDishMapper.add(setmealDish);
        }
    }

    /**
     * 修改状态
     * @param status
     * @param id
     */
    public void setStatus(Integer status,Long id){
        Setmeal setmeal = Setmeal.builder().status(status).id(id).build();
        setmealMapper.update(setmeal);
    }

    /**
     * 批量删除
     * @param ids
     */
    public void deleteByIds(List<Long> ids){
        setmealMapper.deleteByIds(ids);
        setmealDishMapper.deleteBySetmealIds(ids);
    }

    /**
     * 通过id查询
     * @param id
     * @return
     */
    @Transactional
    public SetmealVO getById(Long id){
        var setmeal=setmealMapper.getById(id);
        var setmealDish = setmealDishMapper.getBySetmealId(id);
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);
        setmealVO.setSetmealDishes(setmealDish);
        log.info("查询到的套餐,{}",setmealDish);
        return setmealVO;
    }
}
