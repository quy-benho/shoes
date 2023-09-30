package com.adc.eshop.dao;

import org.apache.ibatis.annotations.Param;

import com.adc.eshop.entity.ShoppingCartItem;

import java.util.List;

public interface ShoppingCartItemMapper {
    int deleteByPrimaryKey(Long cartItemId);

    int insert(ShoppingCartItem record);

    int insertSelective(ShoppingCartItem record);

    ShoppingCartItem selectByPrimaryKey(Long cartItemId);

    ShoppingCartItem selectByUserIdAndGoodsId(
    		@Param("newBeeMallUserId") Long newBeeMallUserId, 
    		@Param("goodsId") Long goodsId,
    		@Param("goodsColor") String goodsColor,
    		@Param("goodsSize") Integer goodsSize
    );

    List<ShoppingCartItem> selectByUserId(@Param("newBeeMallUserId") Long newBeeMallUserId, @Param("number") int number);

    Integer selectCountByUserId(Long newBeeMallUserId);

    int updateByPrimaryKeySelective(ShoppingCartItem record);

    int updateByPrimaryKey(ShoppingCartItem record);

    int deleteBatch(List<Long> ids);
}