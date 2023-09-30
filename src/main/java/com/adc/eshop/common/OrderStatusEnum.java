package com.adc.eshop.common;

public enum OrderStatusEnum {

    DEFAULT(-9, "ERROR"),
    ORDER_PRE_PAY(0, "Chưa thanh toán"),
    OREDER_PAID(1, "Đã thanh toán"),
    OREDER_SUCCESS(2, "Đã giao hàng thành công"),
    OREDER_OUT_OF_STOCK(3, "Đã hết hàng"),
    ORDER_COD(4, "Đã đặt ship COD"),
    ORDER_SHIPPING(5, "Đang giao hàng"),
    ORDER_CLOSED_BY_MALLUSER(-1, "Khách đã hủy"),
    ORDER_CLOSED_BY_EXPIRED(-2, "Quá hạn"),
    ORDER_CLOSED_BY_JUDGE(-3, "Đã đóng");

    private int orderStatus;

    private String name;

    OrderStatusEnum(int orderStatus, String name) {
        this.orderStatus = orderStatus;
        this.name = name;
    }

    public static OrderStatusEnum getOrderStatusEnumByStatus(int orderStatus) {
        for (OrderStatusEnum OrderStatusEnum : OrderStatusEnum.values()) {
            if (OrderStatusEnum.getOrderStatus() == orderStatus) {
                return OrderStatusEnum;
            }
        }
        return DEFAULT;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
