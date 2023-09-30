
package com.adc.eshop.controller.mall;

import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;

import com.adc.eshop.common.Constants;
import com.adc.eshop.common.IndexConfigTypeEnum;
import com.adc.eshop.controller.vo.IndexCarouselVO;
import com.adc.eshop.controller.vo.IndexCategoryVO;
import com.adc.eshop.controller.vo.IndexConfigGoodsVO;
import com.adc.eshop.service.CarouselService;
import com.adc.eshop.service.CategoryService;
import com.adc.eshop.service.IndexConfigService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexMallController {

    @Resource
    private CarouselService carouselService;

    @Resource
    private IndexConfigService indexConfigService;

    @Resource
    private CategoryService categoryService;

    @GetMapping({"/index", "/", "/index.html"})
    public String indexPage(HttpServletRequest request) {
        List<IndexCategoryVO> categories = categoryService.getCategoriesForIndex();
        if (CollectionUtils.isEmpty(categories)) {
            return "error/error_5xx";
        }
        List<IndexCarouselVO> carousels = carouselService.getCarouselsForIndex(Constants.INDEX_CAROUSEL_NUMBER);
        List<IndexConfigGoodsVO> hotGoodses = indexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_HOT.getType(), Constants.INDEX_GOODS_HOT_NUMBER);
        List<IndexConfigGoodsVO> newGoodses = indexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_NEW.getType(), Constants.INDEX_GOODS_NEW_NUMBER);
        List<IndexConfigGoodsVO> recommendGoodses = indexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_RECOMMOND.getType(), Constants.INDEX_GOODS_RECOMMOND_NUMBER);
        request.setAttribute("categories", categories);
        request.setAttribute("carousels", carousels);
        request.setAttribute("hotGoodses", hotGoodses);
        request.setAttribute("newGoodses", newGoodses);
        request.setAttribute("recommendGoodses", recommendGoodses);
        request.setAttribute("allColors", indexConfigService.getAllGoodsColor());
        request.setAttribute("allSizes", indexConfigService.getAllGoodsSize());
        return "pages/index";
    }
}
