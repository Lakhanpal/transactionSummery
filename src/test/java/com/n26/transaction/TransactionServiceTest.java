package com.n26.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.n26.transactions.exception.FutureTransactionError;
import com.n26.transactions.model.Transaction;
import com.n26.transactions.service.StatisticsService;
import com.n26.transactions.serviceimpl.TransactionServiceImpl;
import com.n26.transactions.validator.TransactionValidator;

public class TransactionServiceTest {

    @Mock
    private TransactionValidator transactionValidator;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private StatisticsService statisticsService;

    @Before
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = FutureTransactionError.class)
    public void future_transaction_time_request() {
        Mockito.doCallRealMethod().when(transactionValidator).validateCreateTransaction(Mockito.any(Transaction.class));
        transactionService.saveTransaction(new Transaction(new BigDecimal(120), LocalDateTime.now().plusMinutes(4)));
    }

    @Test
    public void valid_transaction_request() {
        Mockito.doCallRealMethod().when(transactionValidator).validateCreateTransaction(Mockito.any(Transaction.class));
        transactionService.saveTransaction(new Transaction(new BigDecimal(120), LocalDateTime.now().minusSeconds(30)));
        
    }

}
