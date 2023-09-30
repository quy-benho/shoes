
package com.adc.eshop.service;

import java.util.List;

import com.adc.eshop.controller.vo.ShoppingCartItemVO;
import com.adc.eshop.entity.ShoppingCartItem;

public interface ShoppingCartService {

    
    String saveCartItem(ShoppingCartItem shoppingCartItem);

    
    String updateCartItem(ShoppingCartItem shoppingCartItem);

    
    ShoppingCartItem getCartItemById(Long ShoppingCartItemId);

    
    Boolean deleteById(Long ShoppingCartItemId);

    
    List<ShoppingCartItemVO> getMyShoppingCartItems(Long UserId);
}
