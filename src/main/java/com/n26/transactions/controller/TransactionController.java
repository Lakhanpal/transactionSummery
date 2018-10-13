package com.n26.transactions.controller;

import java.time.LocalDateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.n26.transactions.model.Transaction;
import com.n26.transactions.service.TransactionService;
import com.n26.transactions.validator.TransactionValidator;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/transactions")
@Slf4j
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Object> createTransaction(@Valid @NotNull @RequestBody Transaction transaction) {
        log.debug(" Request createTransaction:- " + transaction);
        if (transaction.getTimestamp().isBefore(LocalDateTime.now().minusSeconds(60))) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        transactionService.saveTransaction(transaction);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTransactions() {
        transactionService.deleteAllTransaction();
    }
}
