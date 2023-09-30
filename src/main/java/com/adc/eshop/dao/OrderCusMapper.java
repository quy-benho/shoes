package com.adc.eshop.dao;

import com.adc.eshop.controller.vo.OrderCustomVO;

import java.util.List;

public interface OrderCusMapper {
    List<OrderCustomVO> getTotalByDate();
}
