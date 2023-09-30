
package com.adc.eshop.controller.mall;

import com.adc.eshop.service.GoodsDetailService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adc.eshop.common.Constants;
import com.adc.eshop.controller.vo.GoodsDetailVO;
import com.adc.eshop.controller.vo.IndexCategoryVO;
import com.adc.eshop.controller.vo.SearchPageCategoryVO;
import com.adc.eshop.entity.Goods;
import com.adc.eshop.service.CategoryService;
import com.adc.eshop.service.GoodsService;
import com.adc.eshop.service.IndexConfigService;
import com.adc.eshop.util.BeanUtil;
import com.adc.eshop.util.PageQueryUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class GoodsMallController {

    @Resource
    private GoodsService goodsService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private GoodsDetailService goodsDetailService;
    @Resource
    private IndexConfigService indexConfigService;

    @GetMapping({"/search", "/search.html"})
    public String searchPage(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        if (StringUtils.isEmpty(params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", Constants.GOODS_SEARCH_PAGE_LIMIT);
        if (params.containsKey("goodsCategoryId") && !StringUtils.isEmpty(params.get("goodsCategoryId") + "")) {
            Long categoryId = Long.valueOf(params.get("goodsCategoryId") + "");
            SearchPageCategoryVO searchPageCategoryVO = categoryService.getCategoriesForSearch(categoryId);
            if (searchPageCategoryVO != null) {
                request.setAttribute("goodsCategoryId", categoryId);
                request.setAttribute("searchPageCategoryVO", searchPageCategoryVO);
            }
        }
        if (params.containsKey("orderBy") && !StringUtils.isEmpty(params.get("orderBy") + "")) {
            request.setAttribute("orderBy", params.get("orderBy") + "");
        }
        String keyword = "";
        if (params.containsKey("keyword") && !StringUtils.isEmpty((params.get("keyword") + "").trim())) {
            keyword = params.get("keyword") + "";
        }
        request.setAttribute("keyword", keyword);
        params.put("keyword", keyword);
        params.put("goodsSellStatus", Constants.SELL_STATUS_UP);
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        request.setAttribute("pageResult", goodsService.searchGoods(pageUtil));
        return "pages/search";
    }

    @GetMapping("/goods/detail/{goodsId}")
    public String detailPage(@PathVariable("goodsId") Long goodsId, HttpServletRequest request) {
        if (goodsId < 1) {
        	return "pages/detail-not-found";
        }
        Goods goods = goodsService.getGoodsById(goodsId);
        if (goods == null || Constants.SELL_STATUS_UP != goods.getGoodsSellStatus()) {
            return "pages/detail-not-found";
        }
        GoodsDetailVO goodsDetailVO = new GoodsDetailVO();
        BeanUtil.copyProperties(goods, goodsDetailVO);
        goodsDetailVO.setGoodsCarouselList(goods.getGoodsCarousel().split(","));
        request.setAttribute("goodsDetail", goodsDetailVO);
        return "pages/detail";
    }

    @PostMapping("/goods/detail/{goodsId}")
    @ResponseBody
    public List<Map<String, Object>> detailGoods(@PathVariable("goodsId") Long goodsId) {
    	List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
    	List<String> colors = goodsDetailService.getListColorById(goodsId);
    	colors.forEach(color -> {
    		Map<String, Object> item = new HashMap<String, Object>();
    		item.put("color", color);
    		item.put("sizes", goodsDetailService.getListSizeByColor(color));
    		result.add(item);
    	});
        return result;
    }

    @GetMapping("/listing")
    public String listing(@RequestParam Map<String, Object> params, HttpServletRequest request) {
    	if (StringUtils.isEmpty(params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", 12);
        if (params.containsKey("goodsCategoryId") && !StringUtils.isEmpty(params.get("goodsCategoryId") + "")) {
            Long categoryId = Long.valueOf(params.get("goodsCategoryId") + "");
            SearchPageCategoryVO searchPageCategoryVO = categoryService.getCategoriesForSearch(categoryId);
            if (searchPageCategoryVO != null) {
                request.setAttribute("goodsCategoryId", categoryId);
                request.setAttribute("searchPageCategoryVO", searchPageCategoryVO);
            }
        }
        if (params.containsKey("orderBy") && !StringUtils.isEmpty(params.get("orderBy") + "")) {
            request.setAttribute("orderBy", params.get("orderBy") + "");
        }
        String keyword = "";
        if (params.containsKey("keyword") && !StringUtils.isEmpty((params.get("keyword") + "").trim())) {
            keyword = params.get("keyword") + "";
        }
        if (params.containsKey("size") && !StringUtils.isEmpty(params.get("size") + "")) {
            request.setAttribute("size", params.get("size") + "");
        }
        if (params.containsKey("color") && !StringUtils.isEmpty(params.get("color") + "")) {
        	request.setAttribute("color", params.get("color") + "");
        }
        request.setAttribute("keyword", keyword);
        params.put("keyword", keyword);
        params.put("goodsSellStatus", Constants.SELL_STATUS_UP);
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        request.setAttribute("pageResult", goodsService.searchGoods(pageUtil));
        request.setAttribute("allColors", indexConfigService.getAllGoodsColor());
        request.setAttribute("allSizes", indexConfigService.getAllGoodsSize());
        List<IndexCategoryVO> categories = categoryService.getCategoriesForIndex();
        request.setAttribute("allCategories", categories);
    	return "pages/listing";
    }
}
