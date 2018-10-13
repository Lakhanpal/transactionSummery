package com.n26.utils;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class NumberUtilsTest {

    @Test
    public void max_check() {
        BigDecimal actual = new BigDecimal(10);
        BigDecimal number = NumbersUtils.getMax(actual, null);
        Assert.assertEquals(number, actual);
        number = NumbersUtils.getMax(null, actual);
        Assert.assertEquals(number, actual);
        BigDecimal greaterNumber =  new BigDecimal(15);
        number = NumbersUtils.getMax(greaterNumber, actual);
        Assert.assertEquals(number, greaterNumber);
    }
    
    @Test
    public void min_check() {
        BigDecimal actual = new BigDecimal(10);
        BigDecimal number = NumbersUtils.getMax(actual, null);
        Assert.assertEquals(number, actual);
        number = NumbersUtils.getMax(null, actual);
        Assert.assertEquals(number, actual);
        BigDecimal greaterNumber =  new BigDecimal(15);
        number = NumbersUtils.getMin(greaterNumber, actual);
        Assert.assertEquals(number, actual);
    }

}
