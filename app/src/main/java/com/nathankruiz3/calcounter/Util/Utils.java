package com.nathankruiz3.calcounter.Util;

import java.text.DecimalFormat;

public class Utils {

    public static String formatNumber(int value) {
        DecimalFormat format = new DecimalFormat("#,###,###");
        String formatted = format.format(value);

        return formatted;
    }

}
