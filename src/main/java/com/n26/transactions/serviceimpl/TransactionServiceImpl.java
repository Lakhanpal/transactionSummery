package com.n26.transactions.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.n26.transactions.model.Transaction;
import com.n26.transactions.repository.TransactionRepository;
import com.n26.transactions.service.StatisticsService;
import com.n26.transactions.service.TransactionService;
import com.n26.transactions.validator.TransactionValidator;

import lombok.extern.slf4j.Slf4j;

@Service
public class TransactionServiceImpl implements TransactionService {


    @Autowired
    private StatisticsService statisticsService;
    
    @Autowired
    private TransactionValidator transactionValidator;

    @Override
    public void saveTransaction(Transaction transaction) {
        transactionValidator.validateCreateTransaction(transaction);
        statisticsService.updateStatistics(transaction);
    }

    @Override
    public void deleteAllTransaction() {
        statisticsService.removeAllStatistics();
    }

}
