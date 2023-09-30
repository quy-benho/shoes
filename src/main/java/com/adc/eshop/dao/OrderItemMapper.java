package com.adc.eshop.dao;

import org.apache.ibatis.annotations.Param;

import com.adc.eshop.entity.OrderItem;

import java.util.List;

public interface OrderItemMapper {
    int deleteByPrimaryKey(Long orderItemId);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Long orderItemId);


    List<OrderItem> selectByOrderId(Long orderId);


    List<OrderItem> selectByOrderIds(@Param("orderIds") List<Long> orderIds);


    int insertBatch(@Param("orderItems") List<OrderItem> orderItems);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);
}