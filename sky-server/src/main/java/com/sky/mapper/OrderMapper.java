package com.sky.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.github.pagehelper.Page;
import com.sky.dto.GoodsSalesDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;

@Mapper
public interface OrderMapper {
    
    void insert(Orders orders);

    /**
     * 根据订单号查询订单
     * @param orderNumber
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     * @param orders
     */
    void update(Orders orders);

    /**
     * 根据用户id查询历史订单
     * @param userId
     * @return
     */
    @Select("select * from orders where user_id=#{userId}")
    List<Orders> list(Long userId);

    /**
     * 根据用户id查询历史订单
     * @param ordersPageQueryDTO
     * @return
     */
    Page<Orders> page(OrdersPageQueryDTO ordersPageQueryDTO);
    
    /**
     * 根据订单id查询订单
     * @param id
     * @return
     */
    @Select("select * from orders where id=#{id}")
    Orders getById(Long id);

    /**
     * 根据订单状态和下单时间查询订单
     * @param status
     * @param orderTime
     * @return
     */
    @Select("select from orders where status=#{status} and order_time <#{orderTime}")
    List<Orders> getByStatusAndOrderTime(Integer status,LocalDateTime orderTime);

    Double sumByMap(Map map);

    Integer countByMap(Map map);

    /**
     * 统计指定时间内的销量排名
     * @param begin
     * @param end
     * @return
     */
    List<GoodsSalesDTO> getSalesTop10(LocalDateTime begin,LocalDateTime end);
}
