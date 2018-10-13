package com.n26.transactions.assembler;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.n26.transactions.model.StatisticsData;
import com.n26.transactions.resources.StatisticsResponseResource;

@Component
public class StatisticsAssembler {

    private void halfRoundUpValues(StatisticsData statisticsData) {
        BigDecimal avg = statisticsData.getAvg().setScale(2, BigDecimal.ROUND_HALF_UP);
        statisticsData.setAvg(avg);
        BigDecimal max = statisticsData.getMax().setScale(2, BigDecimal.ROUND_HALF_UP);
        statisticsData.setMax(max);
        BigDecimal min = statisticsData.getMin().setScale(2, BigDecimal.ROUND_HALF_UP);
        statisticsData.setMin(min);
        BigDecimal sum = statisticsData.getSum().setScale(2, BigDecimal.ROUND_HALF_UP);
        statisticsData.setSum(sum);
    }

    public StatisticsResponseResource getResponseData(StatisticsData sd) {
        halfRoundUpValues(sd);
        return StatisticsResponseResource.builder().avg(sd.getAvg().toPlainString())
                .count(sd.getCount().longValue()).max(sd.getMax().toPlainString()).min(sd.getMin().toPlainString())
                .sum(sd.getSum().toPlainString()).build();
    }
}
