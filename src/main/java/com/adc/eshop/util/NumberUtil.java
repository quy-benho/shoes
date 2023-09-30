package com.adc.eshop.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class NumberUtil {

    private NumberUtil() {
    }


    
    public static boolean isPhone(String phone) {
        Pattern pattern = Pattern.compile("/((09|03|07|08|05)+([0-9]{8})\\b)/g");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    
    public static int genRandomNum(int length) {
        int num = 1;
        double random = Math.random();
        if (random < 0.1) {
            random = random + 0.1;
        }
        for (int i = 0; i < length; i++) {
            num = num * 10;
        }
        return (int) ((random * num));
    }

    
    public static String genOrderNo() {
        StringBuffer buffer = new StringBuffer(String.valueOf(System.currentTimeMillis()));
        int num = genRandomNum(4);
        buffer.append(num);
        return buffer.toString();
    }
}
