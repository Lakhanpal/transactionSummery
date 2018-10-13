package com.n26.transactions.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class StatisticsData {
    private BigDecimal sum = new BigDecimal(0.0);
    private BigDecimal count = new BigDecimal(0l);
    private BigDecimal max ;
    private BigDecimal min;
    private BigDecimal avg ;

    public StatisticsData() {
    }

    public StatisticsData buildData(Statistics statistics) {
        this.count = statistics.getCount();
        this.max = statistics.getMax();
        this.min = statistics.getMin();
        this.sum = statistics.getSum();
        return this;
    }

}
