package com.adc.eshop.entity;

public class GoodsDetail {

    private Long goodsDeatailId;

    private String goodsColor;

    private Integer goodsSize;

    private Integer goodsQuantity;

    private Long goodsId;

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    private Byte isDeleted;


    public Long getGoodsDeatailId() {
        return goodsDeatailId;
    }

    public void setGoodsDeatailId(Long goodsDeatailId) {
        this.goodsDeatailId = goodsDeatailId;
    }

    public String getGoodsColor() {
        return goodsColor;
    }

    public void setGoodsColor(String goodsColor) {
        this.goodsColor = goodsColor;
    }

    public Integer getGoodsSize() {
        return goodsSize;
    }

    public void setGoodsSize(Integer goodsSize) {
        this.goodsSize = goodsSize;
    }

    public Integer getGoodsQuantity() {
        return goodsQuantity;
    }

    public void setGoodsQuantity(Integer goodsQuantity) {
        this.goodsQuantity = goodsQuantity;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
}
