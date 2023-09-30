package com.adc.eshop.common;

public enum ServiceResultEnum {
    ERROR("error"),

    SUCCESS("success"),

    DATA_NOT_EXIST("DATA NOT EXIXTS！"),

    SAME_CATEGORY_EXIST("SAME CATEGORY EXIST !"),

    SAME_LOGIN_NAME_EXIST("SAME LOGIN NAME EXITS！"),

    LOGIN_NAME_NULL("LOGIN NAME NULL！"),

    LOGIN_PASSWORD_NULL("LOGIN PASSWORD NULL！"),

    LOGIN_VERIFY_CODE_NULL("LOGIN VERIFY CODE NULL！"),

    LOGIN_VERIFY_CODE_ERROR("LOGIN VERIFY CODE ERROR ！"),

    GOODS_NOT_EXIST("GOODS NOT EXIST！"),

    GOODS_PUT_DOWN("GOODS PUT DOWN !"),

    SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR("Tối đa 5 sản phẩm này trên giỏ hàng！"),

    SHOPPING_CART_ITEM_TOTAL_NUMBER_ERROR("SHOPPING CART ITEM TOTAL NUMBER ERROR！"),

    LOGIN_ERROR("LOGIN ERROR！"),

    LOGIN_USER_LOCKED("LOGIN USER LOCKED！"),

    ORDER_NOT_EXIST_ERROR("ORDER NOT EXIST ERROR"),

    NULL_ADDRESS_ERROR("NULL ADDRESS ERROR"),

    ORDER_PRICE_ERROR("ORDER PRICE ERROR"),

    ORDER_GENERATE_ERROR("ORDER GENERATE ERROR"),

    SHOPPING_ITEM_ERROR("SHOPPING ITEM ERROR"),

    SHOPPING_ITEM_COUNT_ERROR("SHOPPING ITEM COUNT ERROR"),

    ORDER_STATUS_ERROR("ORDER STATUS ERROR"),

    OPERATE_ERROR("OPERATE ERROR"),

    DB_ERROR("database error");

    private String result;

    ServiceResultEnum(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
