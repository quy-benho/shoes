package com.adc.eshop.service;

import com.adc.eshop.controller.vo.IndexCategoryVO;
import com.adc.eshop.controller.vo.SearchPageCategoryVO;
import com.adc.eshop.entity.GoodsCategory;
import com.adc.eshop.entity.GoodsDetail;
import com.adc.eshop.util.PageQueryUtil;
import com.adc.eshop.util.PageResult;

import java.util.List;

public interface GoodsDetailService {

    PageResult getGoodsDetailPage(PageQueryUtil pageUtil);

    String saveGoodsDetail(GoodsDetail goodsDetail);

    String updateGoodsDetail(GoodsDetail goodsDetail);

    Integer deleteGoodsDetail(Long id);

    GoodsDetail getGoodsDetailById(Long id);

    List<GoodsDetail> selectGooDetail(List<Long> parentIds);

    List<String> getListColorById(Long goodsId);

    List<Integer> getListSizeByColor(String goodsColor);
}
