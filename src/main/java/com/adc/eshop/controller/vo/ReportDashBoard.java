package com.adc.eshop.controller.vo;


import lombok.Data;

@Data
public class ReportDashBoard {
    private int orders;
    private int products;
    private int ordersSuccess;
    private double balance;
}
