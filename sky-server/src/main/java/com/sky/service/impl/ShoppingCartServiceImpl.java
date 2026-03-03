package com.sky.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import com.sky.vo.SetmealVO;

import lombok.extern.slf4j.Slf4j;



@Service
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService{
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    /**
     * 添加购物车
     * @param shoppingCartDTO
     */
    @Transactional
    public void add(ShoppingCartDTO shoppingCartDTO){
        //判断商品是否已经存在
        log.info("开始1");
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        //如果已经存在就数量加一
        if(list !=null && list.size()>0){
            log.info("开始2");
            ShoppingCart cart = list.get(0);
            cart.setNumber(cart.getNumber()+1);//update
            shoppingCartMapper.updateNumberById(cart);
        }else{
            //如果不存在就插入数据
            log.info("开始3");
            Long dishId = shoppingCartDTO.getDishId();
            Long setmealId = shoppingCart.getSetmealId();
            if(dishId!=null){
                //本次添加的是菜品
                log.info("开始4");
                Dish dish = dishMapper.getById(dishId);
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());
            }else{
                //本次添加的是套餐
                log.info("开始5");
                SetmealVO setmealVO = setmealMapper.getById(setmealId);
                shoppingCart.setName(setmealVO.getName());
                shoppingCart.setImage(setmealVO.getImage());
                shoppingCart.setAmount(setmealVO.getPrice());

            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insert(shoppingCart);
        }

    }

    /**
     * 展示购物车
     * @return
     */
    public List<ShoppingCart> showShoppingCart(){
        ShoppingCart shoppingCart = ShoppingCart.builder().userId(BaseContext.getCurrentId()).build();
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        return list;
    }

    /**
     * 清空购物车
     */
    public void clean(){
        Long userId = BaseContext.getCurrentId();
        shoppingCartMapper.deleteByUserId(userId);
    }
    
    /**
     * 减少购物车
     * @param shoppingCartDTO
     */
    @Transactional
    public void red(ShoppingCartDTO shoppingCartDTO){
        //判断商品是否已经存在
        log.info("开始1");
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        //因为一定存在才能减少数据
        log.info("开始2");
        ShoppingCart cart = list.get(0);
        cart.setNumber(cart.getNumber()-1);//update
        shoppingCartMapper.updateNumberById(cart);
    }
}
