package com.adc.eshop.controller.vo;

import java.util.Date;

public class OrderCustomVO {
    private int sumPrice;
    private String dateOrder;

    public int getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(int sumPrice) {
        this.sumPrice = sumPrice;
    }

    public String getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(String dateOrder) {
        this.dateOrder = dateOrder;
    }
}
