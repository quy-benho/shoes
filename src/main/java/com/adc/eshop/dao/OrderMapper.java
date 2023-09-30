package com.adc.eshop.dao;

import com.adc.eshop.controller.vo.OrderCustomVO;
import org.apache.ibatis.annotations.Param;

import com.adc.eshop.entity.Order;
import com.adc.eshop.util.PageQueryUtil;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long orderId);

    Order selectByOrderNo(String orderNo);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    List<Order> findOrderList(PageQueryUtil pageUtil);

    int getTotalOrders(PageQueryUtil pageUtil);

    List<Order> selectByPrimaryKeys(@Param("orderIds") List<Long> orderIds);

    int checkOut(@Param("orderIds") List<Long> orderIds);

    int closeOrder(@Param("orderIds") List<Long> orderIds, @Param("orderStatus") int orderStatus);

    int checkDone(@Param("orderIds") List<Long> asList);

    List<OrderCustomVO> getTotalByDate();

    int changeOrderStatus(Long orderId, int orderStatus);
}
