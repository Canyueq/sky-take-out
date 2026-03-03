package com.sky.service;

import java.util.List;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

public interface ShoppingCartService {
    /**
     * 添加购物车
     * @param shoppingCartDTO
     */
    void add(ShoppingCartDTO shoppingCartDTO);

    /**
     * 减少购物车
     * @param shoppingCartDTO
     */
    void red(ShoppingCartDTO shoppingCartDTO);

    /**
     * 展示购物车
     * @return
     */
    public List<ShoppingCart> showShoppingCart();
    
    /**
     * 清空购物车
     */
    public void clean();
}
