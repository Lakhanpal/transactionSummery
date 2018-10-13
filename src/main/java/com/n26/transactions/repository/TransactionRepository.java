package com.n26.transactions.repository;

import org.springframework.stereotype.Repository;

import com.n26.transactions.model.Transaction;

/**
 * 
 * @author lakhan not providing any implementation as we dont want to store transaction data in db.
 *
 */
@Repository
public class TransactionRepository {

    public Long saveTransaction(Transaction transaction) {
        return null;
    }

}
