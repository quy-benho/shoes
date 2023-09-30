package com.adc.eshop.controller.admin;

import com.adc.eshop.common.ServiceResultEnum;
import com.adc.eshop.entity.GoodsCategory;
import com.adc.eshop.entity.GoodsDetail;
import com.adc.eshop.service.CategoryService;
import com.adc.eshop.service.GoodsDetailService;
import com.adc.eshop.util.PageQueryUtil;
import com.adc.eshop.util.Result;
import com.adc.eshop.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
public class GoodsDetailController {

    @Resource
    private GoodsDetailService goodsDetailService;

    @GetMapping("/goodsDetail")
    public String goodsPage(HttpServletRequest request) {
        request.setAttribute("path", "goodsDetail");
        return "admin/goods_detail";
    }

    @RequestMapping(value = "/goodsDetail/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("Abnormal parameter！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(goodsDetailService.getGoodsDetailPage(pageUtil));
    }

    @RequestMapping(value = "/goodsDetail/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody GoodsDetail goodsDetail) {
        if (Objects.isNull(goodsDetail.getGoodsColor())
                || StringUtils.isEmpty(goodsDetail.getGoodsSize())
                || Objects.isNull(goodsDetail.getGoodsQuantity())
                || Objects.isNull(goodsDetail.getGoodsId())) {
            return ResultGenerator.genFailResult("Abnormal parameter！");
        }
        String result = goodsDetailService.saveGoodsDetail(goodsDetail);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    @RequestMapping(value = "/goodsDetail/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody GoodsDetail goodsDetail) {
        if (StringUtils.isEmpty(goodsDetail.getGoodsColor())
                || Objects.isNull(goodsDetail.getGoodsSize())
                || Objects.isNull(goodsDetail.getGoodsQuantity())) {
            return ResultGenerator.genFailResult("Abnormal parameter！");
        }
        String result = goodsDetailService.updateGoodsDetail(goodsDetail);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    @PostMapping(value = "/goodsDetail/delete/{id}")
    @ResponseBody
    public Result delete(@PathVariable("id") Long id) {
        if (goodsDetailService.deleteGoodsDetail(id) > 0) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("Not deleted!");
        }
    }

}
