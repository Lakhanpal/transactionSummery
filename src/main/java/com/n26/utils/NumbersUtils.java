package com.n26.utils;

import java.math.BigDecimal;

public final class NumbersUtils {
    public static final BigDecimal zero = new BigDecimal(0.0);
    public static final BigDecimal one = new BigDecimal(1);

    /**
     * enforcing the class to be used in static way.
     */
    private NumbersUtils() {
        
    }
    
    /**
     * this method will check from 2 numbers if one is null return the other one.
     * if both are null return big decimal 0.
     * if both are not null return the max.
     * @param arg1 first bigdecimal number
     * @param arg2 second bigdecimal number
     * @return maxNumber
     */
    public static BigDecimal getMax(BigDecimal arg1, BigDecimal arg2) {
        if (arg1 == null && arg2 == null) {
            return zero;
        } else if (arg1 == null) {
            return arg2;
        } else if (arg2 == null) {
            return arg1;
        } else {
            return arg1.max(arg2);
        }
    }

    /**
     * this method will check from 2 numbers if one is null return the other one.
     * if both are null return big decimal 0.
     * if both are not null return the min.
     * @param arg1 first bigdecimal number
     * @param arg2 second bigdecimal number
     * @return minNumber
     */
    public static BigDecimal getMin(BigDecimal arg1, BigDecimal arg2) {
        if (arg1 == null && arg2 == null) {
            return zero;
        } else if (arg1 == null) {
            return arg2;
        } else if (arg2 == null) {
            return arg1;
        } else {
            return arg1.min(arg2);
        }
    }

}
