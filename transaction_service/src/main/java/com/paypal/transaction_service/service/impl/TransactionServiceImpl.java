package com.paypal.transaction_service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.transaction_service.entity.Transaction;
import com.paypal.transaction_service.repository.TransactionRepository;
import com.paypal.transaction_service.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    TransactionRepository repository;
    ObjectMapper objectMapper;

    @Override
    public Transaction createTransaction(Transaction request) {
        System.out.println("🚀 Entered createTransaction()");

        Long senderId = request.getSenderId();
        Long receiverId = request.getReceiverId();
        Double amount = request.getAmount();




        Transaction transaction = new Transaction();
        transaction.setSenderId(senderId);
        transaction.setReceiverId(receiverId);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setStatus("SUCCESS");

        System.out.println("📥 Incoming Transaction object: " + transaction);

        Transaction saved = repository.save(transaction);
        System.out.println("💾 Saved Transaction from DB: " + saved);
        return saved;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return repository
                .findAll();
    }
}
