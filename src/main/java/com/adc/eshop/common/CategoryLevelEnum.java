
package com.adc.eshop.common;


public enum CategoryLevelEnum {

    DEFAULT(0, "ERROR"),
    LEVEL_ONE(1, "LEVEL 1"),
    LEVEL_TWO(2, "LEVER 2"),
    LEVEL_THREE(3, "LEVER 3");

    private int level;

    private String name;

    CategoryLevelEnum(int level, String name) {
        this.level = level;
        this.name = name;
    }

    public static CategoryLevelEnum getOrderStatusEnumByLevel(int level) {
        for (CategoryLevelEnum newCategoryLevelEnum : CategoryLevelEnum.values()) {
            if (newCategoryLevelEnum.getLevel() == level) {
                return newCategoryLevelEnum;
            }
        }
        return DEFAULT;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
