package com.sky.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.sky.entity.OrderDetail;

@Mapper
public interface OrderDetailMapper{
    
    /**
     * 批量插入订单详情数据
     * @param orderDetails
     */
    void insertBatch(List<OrderDetail> orderDetails);
    
    /**
     * 根据订单id查询
     * @param id
     * @return
     */
    @Select("select * from order_detail where id=#{id}")
    List<OrderDetail> getById(Long id);
}
