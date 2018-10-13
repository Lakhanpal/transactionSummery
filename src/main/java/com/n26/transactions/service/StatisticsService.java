package com.n26.transactions.service;

import com.n26.transactions.model.StatisticsData;
import com.n26.transactions.model.Transaction;

public interface StatisticsService {

    public void updateStatistics(Transaction transaction);

    public StatisticsData getStatistics();
    
    public void removeAllStatistics();

}
