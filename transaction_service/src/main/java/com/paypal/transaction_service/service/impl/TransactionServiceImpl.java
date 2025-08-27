package com.paypal.transaction_service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.transaction_service.entity.Transaction;
import com.paypal.transaction_service.kafka.KafkaEventProducer;
import com.paypal.transaction_service.repository.TransactionRepository;
import com.paypal.transaction_service.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;
    private final ObjectMapper objectMapper;
    private final KafkaEventProducer kafkaEventProducer;

    @Override
    public Transaction createTransaction(Transaction request) {
        log.info("New transaction started");

        Long senderId = request.getSenderId();
        Long receiverId = request.getReceiverId();
        Double amount = request.getAmount();

        Transaction transaction = new Transaction();
        transaction.setSenderId(senderId);
        transaction.setReceiverId(receiverId);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setStatus("SUCCESS");

        log.info("Incoming Transaction object: {}", transaction);

        Transaction saved = repository.save(transaction);
        log.info("Saved Transaction from DB: {}", saved);

        try {
            String eventPayload  = objectMapper.writeValueAsString(transaction);
            String key = String.valueOf(saved.getId());
            kafkaEventProducer.sendTransactionEvent(key,eventPayload);
        }catch (Exception e){
            log.error("Error while saving transaction: {}", e.getMessage());
            e.printStackTrace();
        }

        return saved;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return repository
                .findAll();
    }
}
