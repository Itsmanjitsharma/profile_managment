package com.cca.dashboard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cca.dashboard.entity.Transaction;
import com.cca.dashboard.repository.TransactionRepository;

@Service
public class TransactionService {
    
    @Autowired
    TransactionRepository transactionRepository;
    public void saveTransaction(Transaction transaction){
        transactionRepository.save(transaction);
    }
}
