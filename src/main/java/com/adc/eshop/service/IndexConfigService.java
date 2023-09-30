
package com.adc.eshop.service;

import java.util.List;

import com.adc.eshop.controller.vo.IndexConfigGoodsVO;
import com.adc.eshop.entity.IndexConfig;
import com.adc.eshop.util.PageQueryUtil;
import com.adc.eshop.util.PageResult;

public interface IndexConfigService {
    
    PageResult getConfigsPage(PageQueryUtil pageUtil);

    String saveIndexConfig(IndexConfig indexConfig);

    String updateIndexConfig(IndexConfig indexConfig);

    IndexConfig getIndexConfigById(Long id);

    List<IndexConfigGoodsVO> getConfigGoodsesForIndex(int configType, int number);

    Boolean deleteBatch(Long[] ids);

    List<String> getAllGoodsColor();

    List<Integer> getAllGoodsSize();

}
