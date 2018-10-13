package com.n26.statistics;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.n26.transactions.model.StatisticsData;
import com.n26.transactions.model.Transaction;
import com.n26.transactions.service.StatisticsService;
import com.n26.transactions.serviceimpl.StatisticsServiceImpl;
import com.n26.utils.NumbersUtils;

public class StatisticsServiceTest {

    private final StatisticsService statisticsService = new StatisticsServiceImpl();

    @Before
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create_older_transaction_addNothing() {
        Transaction transaction = new Transaction(new BigDecimal(120.34), LocalDateTime.now().minusSeconds(100));
        statisticsService.updateStatistics(transaction);
        StatisticsData statistics = statisticsService.getStatistics();
        Assert.assertEquals(NumbersUtils.zero, statistics.getAvg());
        Assert.assertEquals(NumbersUtils.zero, statistics.getMin());
        Assert.assertEquals(NumbersUtils.zero, statistics.getMax());
        Assert.assertEquals(NumbersUtils.zero, statistics.getSum());
        Assert.assertEquals(NumbersUtils.zero, statistics.getCount());

    }

    @Test
    public void create_valid_transaction() {
        Random rand = new Random();
        int  time = rand.nextInt(50) + 1;
        double  amount = rand.nextInt(500) + 1;
        double total = 0;
        int count = 200;
        double min = amount,max = amount;
        for (int i = 0; i < count; i++) {
            time = rand.nextInt(50) + 1;
            amount = rand.nextInt(500) + 1;
            total += amount;
            if(min > amount)
            {
                min = amount;
            }
            if(max < amount)
            {
                max = amount;
            }
            statisticsService.updateStatistics(new Transaction(new BigDecimal(amount), LocalDateTime.now().minusSeconds(time)));
        }
        
        StatisticsData statistics = statisticsService.getStatistics();
        Assert.assertEquals(new BigDecimal(total).divide(new BigDecimal(count)).setScale(2, BigDecimal.ROUND_HALF_UP), statistics.getAvg().setScale(2, BigDecimal.ROUND_HALF_UP));
        Assert.assertEquals(new BigDecimal(min).setScale(2, BigDecimal.ROUND_HALF_UP), statistics.getMin().setScale(2, BigDecimal.ROUND_HALF_UP));
        Assert.assertEquals(new BigDecimal(max).setScale(2, BigDecimal.ROUND_HALF_UP), statistics.getMax().setScale(2, BigDecimal.ROUND_HALF_UP));
        Assert.assertEquals(new BigDecimal(total).setScale(2, BigDecimal.ROUND_HALF_UP), statistics.getSum().setScale(2, BigDecimal.ROUND_HALF_UP));
        Assert.assertEquals(new BigDecimal(count), statistics.getCount());
    }

    @Test
    public void create_tranaction_and_wait_for_60_seconds() throws InterruptedException {
        Transaction transaction = new Transaction(new BigDecimal(120.34), LocalDateTime.now().minusSeconds(50));
        statisticsService.updateStatistics(transaction);
        TimeUnit.SECONDS.sleep(15);
        StatisticsData statistics = statisticsService.getStatistics();
        Assert.assertEquals(NumbersUtils.zero, statistics.getAvg());
        Assert.assertEquals(NumbersUtils.zero, statistics.getMin());
        Assert.assertEquals(NumbersUtils.zero, statistics.getMax());
        Assert.assertEquals(NumbersUtils.zero, statistics.getSum());
        Assert.assertEquals(NumbersUtils.zero, statistics.getCount());

    }

    @Test
    public void delete_and_validate_transaction() {
        Transaction transaction = new Transaction(new BigDecimal(120.34), LocalDateTime.now().minusSeconds(50));
        statisticsService.updateStatistics(transaction);
        statisticsService.removeAllStatistics();
        StatisticsData statistics = statisticsService.getStatistics();
        Assert.assertEquals(NumbersUtils.zero, statistics.getAvg());
        Assert.assertEquals(NumbersUtils.zero, statistics.getMin());
        Assert.assertEquals(NumbersUtils.zero, statistics.getMax());
        Assert.assertEquals(NumbersUtils.zero, statistics.getSum());
        Assert.assertEquals(NumbersUtils.zero, statistics.getCount());
    }
}
