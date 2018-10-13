package com.n26.transactions.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Statistics {
    private long timestamp;
    private BigDecimal sum;
    private BigDecimal max ;
    private BigDecimal min ;
    private BigDecimal count;

}
