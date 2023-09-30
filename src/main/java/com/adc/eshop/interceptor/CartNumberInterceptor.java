
package com.adc.eshop.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.adc.eshop.common.Constants;
import com.adc.eshop.controller.vo.UserVO;
import com.adc.eshop.dao.ShoppingCartItemMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class CartNumberInterceptor implements HandlerInterceptor {

    @Autowired
    private ShoppingCartItemMapper shoppingCartItemMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        if (null != request.getSession() && null != request.getSession().getAttribute(Constants.MALL_USER_SESSION_KEY)) {
            UserVO userVO = (UserVO) request.getSession().getAttribute(Constants.MALL_USER_SESSION_KEY);
            Integer totalCartItem = shoppingCartItemMapper.selectCountByUserId(userVO.getUserId());
            if (totalCartItem == null) {
            	totalCartItem = 0;
            }
            userVO.setShopCartItemCount(totalCartItem);
            request.getSession().setAttribute(Constants.MALL_USER_SESSION_KEY, userVO);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
