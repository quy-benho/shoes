
package com.adc.eshop.service;

import java.util.List;

import com.adc.eshop.entity.Goods;
import com.adc.eshop.util.PageQueryUtil;
import com.adc.eshop.util.PageResult;

public interface GoodsService {
    
    PageResult getGoodsPage(PageQueryUtil pageUtil);

    
    String saveGoods(Goods goods);

    
    void batchSaveGoods(List<Goods> newGoodsList);

    
    String updateGoods(Goods goods);

    
    Goods getGoodsById(Long id);

    
    Boolean batchUpdateSellStatus(Long[] ids,int sellStatus);

    
    PageResult searchGoods(PageQueryUtil pageUtil);
}
