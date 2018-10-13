package com.n26.transactions.service;

import com.n26.transactions.model.Transaction;

public interface TransactionService {

    public void saveTransaction(Transaction transaction);
    
    public void deleteAllTransaction();
    
}
