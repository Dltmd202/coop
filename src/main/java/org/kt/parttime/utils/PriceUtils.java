package org.kt.parttime.utils;

import java.text.DecimalFormat;

public class PriceUtils {
    public static String format(int price){
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(price);
    }
}
