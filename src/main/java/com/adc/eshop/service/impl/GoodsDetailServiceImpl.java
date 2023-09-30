package com.adc.eshop.service.impl;

import com.adc.eshop.common.ServiceResultEnum;
import com.adc.eshop.dao.GoodsDetailMapper;
import com.adc.eshop.entity.GoodsCategory;
import com.adc.eshop.entity.GoodsDetail;
import com.adc.eshop.service.GoodsDetailService;
import com.adc.eshop.service.GoodsService;
import com.adc.eshop.util.PageQueryUtil;
import com.adc.eshop.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GoodsDetailServiceImpl implements GoodsDetailService {

    @Autowired
    GoodsDetailMapper goodsDetailMapper;

    @Override
    public PageResult getGoodsDetailPage(PageQueryUtil pageUtil) {
        List<GoodsDetail> goodsDetails = goodsDetailMapper.findGoodsDetailList(pageUtil);
        int total = goodsDetailMapper.getTotalGoodsCategories(pageUtil);
        PageResult pageResult = new PageResult(goodsDetails, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String saveGoodsDetail(GoodsDetail goodsDetail) {
        goodsDetail.setIsDeleted((byte) 0);
        if (goodsDetailMapper.insertSelective(goodsDetail) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String updateGoodsDetail(GoodsDetail goodsDetail) {
        GoodsDetail temp = goodsDetailMapper.selectByPrimaryKey(goodsDetail.getGoodsDeatailId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        if (goodsDetailMapper.updateByPrimaryKeySelective(goodsDetail) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public GoodsDetail getGoodsDetailById(Long id) {
        return null;
    }

    @Override
    public List<GoodsDetail> selectGooDetail(List<Long> parentIds) {
        return null;
    }

    @Override
    public List<String> getListColorById(Long goodsId) {
        return goodsDetailMapper.getListColorById(goodsId);
    }

    @Override
    public List<Integer> getListSizeByColor(String goodsColor) {
        return goodsDetailMapper.getListSizeByColor(goodsColor);
    }

	@Override
	public Integer deleteGoodsDetail(Long id) {
		return goodsDetailMapper.deleteByPrimaryKey(id);
	}
}
