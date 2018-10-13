package com.n26.transactions.serviceimpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.n26.transactions.model.Statistics;
import com.n26.transactions.model.StatisticsData;
import com.n26.transactions.model.Transaction;
import com.n26.transactions.service.StatisticsService;
import com.n26.utils.DateUtils;
import com.n26.utils.NumbersUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StatisticsServiceImpl implements StatisticsService {

    private static final Integer STATS_SEC = 60;
    private static final Map<Integer, Statistics> statisticsMap = new ConcurrentHashMap<>(STATS_SEC);

    /**
     * this method will compute the statistics and update in statistics map.
     */
    @Override
    public void updateStatistics(Transaction transaction) {
        log.debug("compute statistics.");
        Long transactionTimeInMillis = DateUtils.getEpocTime(transaction.getTimestamp());
        if ((System.currentTimeMillis() - transactionTimeInMillis) / 1000 < STATS_SEC) {
            int second = transaction.getTimestamp().getSecond();
            statisticsMap.compute(second, (key, value) -> {
                if (value == null || (System.currentTimeMillis() - value.getTimestamp()) / 1000 >= STATS_SEC) {
                    value = new Statistics();
                    value.setTimestamp(transactionTimeInMillis);
                    value.setSum(transaction.getAmount());
                    value.setMax(transaction.getAmount());
                    value.setMin(transaction.getAmount());
                    value.setCount(new BigDecimal(1));
                    return value;
                }
                value.setCount(value.getCount().add(NumbersUtils.one));
                value.setSum(value.getSum().add(transaction.getAmount()));
                value.setMax(NumbersUtils.getMax(transaction.getAmount(), value.getMax()));
                value.setMin(NumbersUtils.getMin(transaction.getAmount(), value.getMin()));
                return value;
            });
        }

    }

    /**
     * this method filters last 60 seconds data and calculate summary then return the statistics data.
     */
    @Override
    public StatisticsData getStatistics() {
        StatisticsData summary = statisticsMap.values().stream()
                .filter(s -> (System.currentTimeMillis() - s.getTimestamp()) / 1000 < STATS_SEC)
                .map(new StatisticsData()::buildData).reduce(new StatisticsData(), (s1, s2) -> {
                    s1.setSum(s1.getSum().add(s2.getSum()));
                    s1.setCount(s1.getCount().add(s2.getCount()));
                    s1.setMax(NumbersUtils.getMax(s1.getMax(), s2.getMax()));
                    s1.setMin(NumbersUtils.getMin(s1.getMin(), s2.getMin()));
                    return s1;
                });
        summary.setMin(NumbersUtils.getMin(summary.getMin(), null));
        summary.setMax(NumbersUtils.getMax(summary.getMax(), null));
        summary.setAvg(summary.getCount().compareTo(NumbersUtils.zero) > 0
                ? summary.getSum().divide(summary.getCount(), 2, RoundingMode.HALF_UP) : NumbersUtils.zero);

        return summary;
    }

    /**
     * this will remove the summary data from the map.
     */
    @Override
    public void removeAllStatistics() {
        if (!statisticsMap.isEmpty()) {
            statisticsMap.clear();
        }
    }

}
