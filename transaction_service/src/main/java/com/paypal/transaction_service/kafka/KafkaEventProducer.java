package com.paypal.transaction_service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaEventProducer {

    private static final String TOPIC = "txn-initiated";

    private final KafkaTemplate<String,String> kafkaTemplate;

    private final ObjectMapper objectMapper;


    public void sendTransactionEvent(String key,String message){
        objectMapper.registerModule(new JavaTimeModule());

        log.info("Sending to Kafka | Topic: {} | Key: {} | Message: {}", TOPIC, key, message);

        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(TOPIC, key, message);

        future.thenAccept(result -> {
            RecordMetadata metadata = result.getRecordMetadata();
            log.info("✅ Message sent successfully | Topic: {} | Partition: {} | Offset: {} | Key: {} | Message: {}",
                    metadata.topic(), metadata.partition(), metadata.offset(), key, message);
        }).exceptionally(ex -> {
            log.error("❌ Failed to send message | Topic: {} | Key: {} | Message: {} | Error: {}",
                    TOPIC, key, message, ex.getMessage(), ex);
            return null;
        });
    }


}
