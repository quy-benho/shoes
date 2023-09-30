package com.adc.eshop.dao;

import com.adc.eshop.entity.GoodsDetail;
import com.adc.eshop.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsDetailMapper {
    int deleteByPrimaryKey(Long goodsDetailId);

    int insert(GoodsDetail record);

    int insertSelective(GoodsDetail record);

    GoodsDetail selectByPrimaryKey(Long goodsDetailId);

    int updateByPrimaryKeySelective(GoodsDetail record);

    int updateByPrimaryKey(GoodsDetail record);

    List<GoodsDetail> findGoodsDetailList(PageQueryUtil pageUtil);

    int getTotalGoodsCategories(PageQueryUtil pageUtil);

    List<String> getListColorById(@Param("goodsId") Long goodsId);

    List<Integer> getListSizeByColor(@Param("goodsColor") String goodsColor);

}
