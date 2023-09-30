
package com.adc.eshop.service;

import java.util.List;

import com.adc.eshop.controller.vo.*;
import com.adc.eshop.entity.Order;
import com.adc.eshop.util.PageQueryUtil;
import com.adc.eshop.util.PageResult;

public interface OrderService {
    
    PageResult getOrdersPage(PageQueryUtil pageUtil);

    
    String updateOrderInfo(Order order);

    
    String checkDone(Long[] ids);

    
    String checkOut(Long[] ids);

    
    String closeOrder(Long[] ids);

    
    String saveOrder(UserVO user, List<ShoppingCartItemVO> myShoppingCartItems);

    
    OrderDetailVO getOrderDetailByOrderNo(String orderNo, Long userId);

    
    Order getOrderByOrderNo(String orderNo);

    
    PageResult getMyOrders(PageQueryUtil pageUtil);

    
    String cancelOrder(String orderNo, Long userId);

    
    String finishOrder(String orderNo, Long userId);

    
    String paySuccess(String orderNo, int payType);

    
    List<OrderItemVO> getOrderItems(Long id);
    

    List<OrderCustomVO> getTotalByDate();


    String changeOrderStatus(Order order);
}
