package com.n26.transactions.validator;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.n26.transactions.exception.FutureTransactionError;
import com.n26.transactions.model.Transaction;

@Component
public class TransactionValidator {

    public void validateCreateTransaction(Transaction transaction) {
        if (transaction.getTimestamp().isAfter(LocalDateTime.now())) {
            throw new FutureTransactionError();
        }

    }

}
